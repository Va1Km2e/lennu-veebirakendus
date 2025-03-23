<template>
  <div class="flight-list">
    <h2>Flight Search</h2>
    <h3>Filter by criteria</h3>
    <div class="input">

      <label for="destinationCity">Destination City</label>
      <input type="text" id="destinationCity" v-model="destinationCity" placeholder="e.g. London">

      <label for="priceRange">Min Max price sliders</label>
      <div class="price-range">
        <input type="range" id="minPrice" v-model="minPrice" :min="0" :max="10000" :step="50">
        <input type="range" id="maxPrice" v-model="maxPrice" :min="0" :max="10000" :step="50">
        <div class="price-display-container">
          <span class="price-display">{{ minPrice | currency }} - {{ maxPrice | currency }}</span>
        </div>
      </div>

      <button @click="applyFilters">Apply Filters</button>
    </div>

    <button @click="changeSortDirection">Change Sort Direction</button>

    <table v-if="flights.length" class="styled-table">
      <thead>
      <tr>
        <th>Flight Number</th>
        <th>Destination City</th>
        <th>Price</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="flight in flights" :key="flight.id" @click="handleRowClick(flight)" role="button">
        <td>{{ flight.flightNumber }}</td>
        <td>{{ flight.destinationCity }}</td>
        <td>{{ flight.basePrice | currency }}</td>
      </tr>
      </tbody>
    </table>

    <div class="pagination-controls" v-if="totalPages > 1">
      <button @click="changePage(currentPage - 1)" :disabled="currentPage === 1">
        <
      </button>
      <span>Page {{ currentPage }} of {{ totalPages }}</span>
      <button @click="changePage(currentPage + 1)" :disabled="currentPage === totalPages">
        >
      </button>
    </div>

    <p v-else class="no-data">No More Flights available</p>

    <p v-if="error" class="error">{{ error }}</p>
  </div>
</template>

<script>
import { ref, onMounted } from "vue";
import axios from "axios";
import { useRoute, useRouter } from "vue-router";

export default {
  name: "FlightSearch",
  setup(props, { emit }) {
    const flights = ref([]);
    const destinationCity = ref(null);
    const minPrice = ref(null);
    const maxPrice = ref(null);
    const currentPage = ref(1);
    const totalPages = ref(1);
    const pageSize = ref(10);
    const error = ref("");
    const router = useRouter();
    const route = useRoute();
    const sortDirection = ref("DESC");

    const fetchFlights = async () => {
      try {
        const queryParams = {
          page: currentPage.value - 1,
          size: pageSize.value,
          sortDirection: sortDirection.value,
        };
        if (destinationCity.value) queryParams.destinationCity = destinationCity.value;
        if (minPrice.value) queryParams.minPrice = minPrice.value;
        if (maxPrice.value) queryParams.maxPrice = maxPrice.value;

        const response = await axios.get('/api/flight', { params: queryParams });

        if (response.data.content && Array.isArray(response.data.content)) {
          flights.value = response.data.content.map(flight => ({
            id: flight.id,
            flightNumber: flight.flightNumber,
            destinationCity: flight.destinationCity,
            basePrice: flight.basePrice,
          }));
        }

        currentPage.value = response.data.pageNumber + 1;
        totalPages.value = response.data.totalPages;
      } catch (err) {
        error.value = 'Error fetching flights: ' + err.message;
      }
    };

    const applyFilters = () => {
      currentPage.value = 1;
      router.push({
        query: {
          ...route.query,
          destinationCity: destinationCity.value || undefined,
          minPrice: minPrice.value || undefined,
          maxPrice: maxPrice.value || undefined,
          page: currentPage.value,
          size: pageSize.value,
        },
      });
      fetchFlights();
    };

    // Change page for pagination
    const changePage = (page) => {
      if (page > 0 && page <= totalPages.value) {
        currentPage.value = page;
        fetchFlights();
      }
    };

    const changeSortDirection = () => {
      sortDirection.value = sortDirection.value === "ASC" ? "DESC" : "ASC";
      router.push({
        ...route.query,
        sortDirection: sortDirection.value,
        page: currentPage.value,
        size: pageSize.value,
      });
      fetchFlights();
    };

    const handleRowClick = (flight) => {
      router.push({ name: 'flight-detail', params: { flightId: flight.id } });
    };

    onMounted(() => {
      const { destinationCity: city, minPrice: min, maxPrice: max, page, size } = route.query;

      destinationCity.value = city || null;
      minPrice.value = min ? Number(min) : null;
      maxPrice.value = max ? Number(max) : null;
      currentPage.value = page ? Number(page) : 1;
      pageSize.value = size ? Number(size) : 10;

      fetchFlights();
    });

    return {
      flights,
      destinationCity,
      minPrice,
      maxPrice,
      currentPage,
      totalPages,
      error,
      fetchFlights,
      applyFilters,
      changePage,
      handleRowClick,
      changeSortDirection,
    };
  },
};
</script>

<style scoped>
.flight-list {
  max-width: 1200px;
  margin: 3rem auto;
  padding: 2rem;
  background-color: #f4f4f9;
  border-radius: 8px;
  box-shadow: 0 10px 15px rgba(0, 0, 0, 0.1);
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

h2 {
  color: #333;
  font-size: 2.4rem;
  margin-bottom: 1.5rem;
}

h3 {
  color: #555;
  font-size: 1.2rem;
  margin-bottom: 0.8rem;
}

.input {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  gap: 1rem;
  margin-bottom: 2rem;
}

label {
  font-size: 1rem;
  color: #555;
  flex: 1;
}

input[type="text"], input[type="date"], input[type="number"] {
  padding: 0.75rem;
  width: 30%;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #fff;
  transition: border 0.3s ease;
}

input[type="text"]:focus, input[type="date"]:focus, input[type="number"]:focus {
  border-color: #00b4d8;
  outline: none;
}

button {
  padding: 0.7rem 1.2rem;
  font-size: 1rem;
  background-color: #00b4d8;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #007a8c;
}

button:disabled {
  background-color: #dcdcdc;
  cursor: not-allowed;
}

.styled-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 2rem;
  font-size: 1rem;
  background-color: #fff;
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.1);
}

.styled-table th, .styled-table td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

.styled-table th {
  background-color: #00b4d8;
  color: #fff;
  font-weight: 600;
}

.styled-table tr:nth-child(even) {
  background-color: #f9f9f9;
}

.styled-table tr:hover {
  background-color: #f1f1f1;
}

.styled-table tr[role="button"] {
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.styled-table tr[role="button"]:hover {
  background-color: #e0f7fa;
}

.pagination-controls {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 0.5rem;
  margin-top: 2rem;
}

.pagination-controls button {
  background-color: #00b4d8;
  padding: 0.5rem 1rem;
  border-radius: 5px;
  color: #fff;
}

.pagination-controls button:disabled {
  background-color: #dcdcdc;
}

.no-data {
  font-size: 1.4rem;
  color: #888;
  margin-top: 2rem;
}

.error {
  margin-top: 2rem;
  color: red;
  font-weight: 600;
}

@media (max-width: 768px) {
  .input {
    flex-direction: column;
    gap: 1rem;
  }

  input[type="text"], input[type="date"], input[type="number"] {
    width: 100%;
  }

  button {
    width: 100%;
  }

  .pagination-controls {
    flex-direction: column;
  }
}
</style>
