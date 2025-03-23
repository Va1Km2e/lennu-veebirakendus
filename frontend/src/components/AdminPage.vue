<template>
  <div class="admin-page">
    <h1>Admin Panel</h1>

    <!-- User Registration Form -->
    <section>
      <h2>Register User</h2>
      <form @submit.prevent="submitUserForm">
        <div>
          <label for="username">Username:</label>
          <input v-model="userForm.username" type="text" id="username" required />
        </div>
        <div>
          <label for="firstName">First Name:</label>
          <input v-model="userForm.firstName" type="text" id="firstName" required />
        </div>
        <div>
          <label for="lastName">Last Name:</label>
          <input v-model="userForm.lastName" type="text" id="lastName" required />
        </div>
        <div>
          <label for="password">Password:</label>
          <input v-model="userForm.password" type="password" id="password" required />
        </div>
        <button type="submit">Register User</button>
      </form>
    </section>

    <!-- Flight Registration Form -->
    <section>
      <h2>Register Flight</h2>
      <form @submit.prevent="submitFlightForm">
        <div>
          <label for="flightNumber">Flight Number:</label>
          <input v-model="flightForm.flightNumber" type="text" id="flightNumber" required />
        </div>
        <div>
          <label for="departureCity">Departure City:</label>
          <input v-model="flightForm.departureCity" type="text" id="departureCity" required />
        </div>
        <div>
          <label for="destinationCity">Destination City:</label>
          <input v-model="flightForm.destinationCity" type="text" id="destinationCity" required />
        </div>
        <div>
          <label for="departureTime">Departure Time:</label>
          <input v-model="flightForm.departureTime" type="datetime-local" id="departureTime" required />
        </div>
        <div>
          <label for="arrivalTime">Arrival Time:</label>
          <input v-model="flightForm.arrivalTime" type="datetime-local" id="arrivalTime" required />
        </div>
        <div>
          <label for="basePrice">Base seat price:</label>
          <input v-model="flightForm.basePrice" type="number" step="0.01" id="basePrice" required />
        </div>
        <div>
          <label for="availableSeats">Available Seats:</label>
          <input v-model="flightForm.availableSeats" type="number" id="availableSeats" required />
        </div>
        <button type="submit">Register Flight</button>
      </form>
    </section>

    <!-- Seat Class Registration Form -->
    <section>
      <h2>Register Seat Class</h2>
      <form @submit.prevent="submitSeatClassForm">
        <div>
          <label for="className">Class Name:</label>
          <input v-model="seatClassForm.className" type="text" id="className" required />
        </div>
        <div>
          <label for="description">Description:</label>
          <input v-model="seatClassForm.description" type="text" id="description" required />
        </div>
        <div>
          <label for="priceModifier">Price Modifier:</label>
          <input v-model="seatClassForm.priceModifier" type="number" step="0.01" id="priceModifier" required />
        </div>
        <button type="submit">Register Seat Class</button>
      </form>
    </section>

    <!-- Seat Registration Form -->
    <section>
      <h2>Register Seat</h2>
      <form @submit.prevent="submitSeatForm">
        <div>
          <label for="seatNumber">Seat Number:</label>
          <input v-model="seatForm.seatNumber" type="text" id="seatNumber" required />
        </div>
        <div>
          <label for="flightId">Flight ID:</label>
          <input v-model="seatForm.flightId" type="number" id="flightId" required />
        </div>
        <div>
          <label for="classId">Class ID:</label>
          <select v-model="seatForm.classId" id="classId" required>
            <option value="1">Business</option>
            <option value="2">Economy</option>
          </select>
        </div>
        <button type="submit">Register Seat</button>
      </form>
    </section>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      userForm: {
        username: '',
        firstName: '',
        lastName: '',
        password: ''
      },
      flightForm: {
        flightNumber: '',
        departureCity: '',
        destinationCity: '',
        departureTime: '',
        arrivalTime: '',
        basePrice: 0,
        availableSeats: 0
      },
      seatClassForm: {
        className: '',
        description: '',
        priceModifier: 1.0
      },
      seatForm: {
        seatNumber: '',
        flightId: '',
        classId: 1
      }
    };
  },
  methods: {
    async submitUserForm() {
      try {
        await axios.post('http://localhost:8080/api/user/register', this.userForm);
        alert('User registered successfully!');
        this.resetUserForm();
      } catch (error) {
        console.error('Error registering user:', error);
        alert('Failed to register user.');
      }
    },
    resetUserForm() {
      this.userForm = {username: '', firstName: '', lastName: '', password: ''};
    },
    async submitFlightForm() {
      try {
        await axios.post('http://localhost:8080/api/flight/register', this.flightForm);
        alert('Flight registered successfully!');
        this.resetFlightForm();
      } catch (error) {
        console.error('Error registering flight:', error);
        alert('Failed to register flight.');
      }
    },
    resetFlightForm() {
      this.flightForm = {
        flightNumber: '',
        departureCity: '',
        destinationCity: '',
        departureTime: '',
        arrivalTime: '',
        basePrice: 0,
        availableSeats: 0
      };
    },
    async submitSeatClassForm() {
      try {
        await axios.post('http://localhost:8080/api/seat-class/register', this.seatClassForm);
        alert('Seat Class registered successfully!');
        this.resetSeatClassForm();
      } catch (error) {
        console.error('Error registering seat class:', error);
        alert('Failed to register seat class.');
      }
    },
    resetSeatClassForm() {
      this.seatClassForm = {className: '', description: '', priceModifier: 1.0};
    },
    async submitSeatForm() {
      try {
        await axios.post('http://localhost:8080/api/seat/register', this.seatForm);
        alert('Seat registered successfully!');
        this.resetSeatForm();
      } catch (error) {
        console.error('Error registering seat:', error);
        alert('Failed to register seat.');
      }
    },
    resetSeatForm() {
      this.seatForm = {seatNumber: '', flightId: '', classId: 1};
    }
  }
};
</script>

<style scoped>
.admin-page {
  padding: 20px;
}

section {
  margin-bottom: 30px;
}

form {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

input, select, button {
  padding: 10px;
  font-size: 16px;
}
</style>
