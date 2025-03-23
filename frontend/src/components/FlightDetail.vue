<template>
  <div class="flight-detail">
    <h2>Flight details</h2>
    <div v-if="flight">
      <p><strong>Flight number:</strong> {{ flight.flightNumber }}</p>
      <p><strong>Destination:</strong> {{ flight.destinationCity }}</p>
      <p><strong>Price:</strong> {{ flight.basePrice }}€</p>
      <p><strong>Flight departure:</strong> {{ flight.departureTime }}</p>
      <p><strong>flight arrival:</strong> {{ flight.arrivalTime }}</p>

      <div>
        <h2>
          <input type="checkbox" v-model="showSeatsWith1Or6" />
          Filter by window seats
        </h2>
      </div>

      <div class="container">
        <div class="seat-grid-container">
          <h3>Flight seat map:</h3>
          <div v-if="seats.length > 0">
            <div class="seat-grid">
              <div
                  v-for="(seat, index) in sortedSeats"
                  :key="seat.id"
                  :class="[
                    'seat',
                    seat.occupied ? 'occupied' : 'available',
                    isSeatSelected(seat.id) ? 'selected' : '',
                    { 'glowing': showSeatsWith1Or6 && (seat.seatNumber.includes('1') || seat.seatNumber.includes('6')) },
                    { 'invisible': showSeatsWith1Or6 && !(seat.seatNumber.includes('1') || seat.seatNumber.includes('6')) }
                  ]"
                  @click="seat.occupied ? null : toggleSeatSelection(seat.id)">
                <div class="seat-info">
                  <span>{{ seat.seatNumber }}</span>
                  <div>
                    <span class="seat-price">{{ seat.price }}€</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div v-else>
            <p>Seats are not available.</p>
          </div>
        </div>

        <div class="booking-form-container">
          <h2>Book the seats</h2>

          <form @submit.prevent="submitBooking">
            <div class="form-group">
              <label for="name">Name:</label>
              <input type="text" id="name" v-model="form.name" required />
            </div>
            <div class="form-group">
              <label for="phoneNumber">Phone Number:</label>
              <input type="text" id="phoneNumber" v-model="form.phoneNumber" required />
            </div>
            <div class="form-group">
              <label for="totalPrice">Total price (€):</label>
              <input type="text" id="totalPrice" v-model="totalPrice" readonly />
            </div>

            <h4>Selected Seats:</h4>
            <ul>
              <li v-for="seatId in selectedSeats" :key="seatId">{{ getSeatNumber(seatId) }}</li>
            </ul>

            <button type="submit" :disabled="selectedSeats.length === 0">Order</button>
          </form>
        </div>
      </div>
    </div>

    <div v-else>
      <p>Data is not available.</p>
    </div>
  </div>
</template>


<script>
import { ref, onMounted, computed } from 'vue';
import axios from 'axios';

export default {
  name: 'FlightDetail',
  props: {
    flightId: String,
  },
  setup(props) {
    const showSeatsWith1Or6 = ref(false);
    const flight = ref(null);
    const seats = ref([]);
    const error = ref(null);
    const selectedSeats = ref([]);
    const form = ref({
      name: '',
      phoneNumber: '',
    });

    const totalPrice = computed(() => {
      return selectedSeats.value.reduce((sum, seatId) => {
        const seat = seats.value.find(s => s.id === seatId);
        return sum + (seat ? seat.price : 0);
      }, 0);
    });

    const sortBy = ref('price');
    const sortDirection = ref('ASC');
    const currentPage = ref(0);
    const pageSize = ref(60);

    const fetchFlightDetail = async () => {
      try {
        const response = await axios.get(`/api/flight/${props.flightId}`);
        flight.value = response.data;
        fetchSeats();
      } catch (err) {
        error.value = 'Lennu detailide laadimine ebaõnnestus: ' + err.message;
      }
    };

    const fetchSeats = async () => {
      try {
        const response = await axios.get('/api/seat', {
          params: {
            flightId: props.flightId,
            sortBy: sortBy.value,
            sortDirection: sortDirection.value,
            page: currentPage.value,
            size: pageSize.value,
          },
        });
        seats.value = response.data.content;
      } catch (err) {
        error.value = 'Istmete laadimine ebaõnnestus: ' + err.message;
      }
    };

    const sortedSeats = computed(() => {
      return seats.value.sort((a, b) => {
        const seatA = a.seatNumber;
        const seatB = b.seatNumber;

        const letterA = seatA[0];
        const letterB = seatB[0];

        const numberA = parseInt(seatA.slice(1), 10);
        const numberB = parseInt(seatB.slice(1), 10);

        if (letterA < letterB) return -1;
        if (letterA > letterB) return 1;
        if (numberA < numberB) return -1;
        if (numberA > numberB) return 1;
        return 0;
      });
    });

    const isSeatSelected = (seatId) => {
      return selectedSeats.value.includes(seatId);
    };

    const toggleSeatSelection = (seatId) => {
      if (selectedSeats.value.includes(seatId)) {
        selectedSeats.value = selectedSeats.value.filter(id => id !== seatId);
      } else {
        selectedSeats.value.push(seatId);
      }
    };

    const getSeatNumber = (seatId) => {
      const seat = seats.value.find(s => s.id === seatId);
      return seat ? seat.seatNumber : '';
    };

    const submitBooking = async () => {
      const bookingData = {
        userId: 1,
        flightId: props.flightId,
        status: 'PENDING',
        seatIds: selectedSeats.value,
        bookingDate: new Date().toISOString(),
        totalPrice: totalPrice.value,
      };

      try {
        const response = await axios.post('http://localhost:8080/api/booking/register', bookingData, {
          headers: {
            'Content-Type': 'application/json',
          },
        });
        alert('Broneering õnnestus!');
        form.value.name = '';
        form.value.phoneNumber = '';
        selectedSeats.value = [];
      } catch (err) {
        alert('Broneering ebaõnnestus: ' + err.message);
      }
    };

    onMounted(() => {
      if (props.flightId) {
        fetchFlightDetail();
      }
    });

    return {
      flight,
      seats,
      error,
      selectedSeats,
      sortedSeats,
      form,
      totalPrice,
      isSeatSelected,
      toggleSeatSelection,
      getSeatNumber,
      submitBooking,
      showSeatsWith1Or6,
    };
  },
};
</script>

<style scoped>
.flight-detail {
  font-family: Arial, sans-serif;
}

.container {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.seat-grid-container {
  width: 50%;
}

.booking-form-container {
  width: 50%;
  padding-left: 20px;
}

.seat-grid {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  gap: 10px;
}

.seat {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f0f0f0;
  padding: 10px;
  border-radius: 5px;
  font-weight: bold;
  cursor: pointer;
  width: 100px;
  height: 100px;
}

.seat.available {
  background-color: #4CAF50;
  color: white;
}

.seat.occupied {
  background-color: #FF6347;
  color: white;
  cursor: not-allowed;
}

.seat.selected {
  background-color: yellow;
  color: black;
}

.seat.glowing {
  box-shadow: 0 0 10px 2px rgba(0, 255, 255, 0.8);
}

.seat.invisible {
  opacity: 0;
  pointer-events: none;
}

.seat-info {
  text-align: center;
}

.seat-price {
  font-size: 12px;
  color: #333;
}

form input {
  padding: 8px;
  margin-top: 5px;
  width: 100%;
  box-sizing: border-box;
}

button {
  padding: 10px;
  background-color: #4CAF50;
  color: white;
  border: none;
  cursor: pointer;
  font-size: 16px;
}

button:disabled {
  background-color: #ccc;
}
</style>

