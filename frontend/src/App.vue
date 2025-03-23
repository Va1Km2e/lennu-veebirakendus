<script setup>
import { RouterLink, RouterView } from 'vue-router'
import {ref, provide, onMounted} from 'vue';

const isLoggedIn = ref(false);

provide('isLoggedIn', isLoggedIn);

onMounted(() => {
  const token = localStorage.getItem('authToken');
  if (token) {
    isLoggedIn.value = true;
  }
});
</script>

<template>
  <header>
    <div class="wrapper">
      <nav>
        <RouterLink to="/">Home</RouterLink>
        <RouterLink to="/admin">admin</RouterLink>
      </nav>
    </div>
  </header>

  <RouterView />
</template>

<style scoped>
header {
  position: fixed; /* Fix the header to the top */
  top: 0;
  left: 0;
  width: 100%;
  background-color: #00b4d8; /* Add a background color for contrast */
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); /* Add a subtle shadow for separation */
  z-index: 1000; /* Ensure it appears above other elements */
  line-height: 1.5;
}

.wrapper {
  max-width: 1200px; /* Optional: Center the content */
  margin: 0 auto;
  padding: 1rem;
}

nav {
  display: flex;
  justify-content: space-between; /* Space out items: left for links, right for the icon */
  align-items: center; /* Vertically center the content */
}

.nav-links {
  display: flex;
  gap: 1rem; /* Space between the nav links */
}

.user-icon i {
  font-size: 1.5rem; /* Icon size */
  color: #333; /* Default icon color */
  transition: color 0.3s;
}

.user-icon:hover i {
  color: #023e8a; /* Hover color for the icon */
}

nav a {
  display: inline-block;
  padding: 0.5rem 1rem; /* Spacing for clickable area */
  text-decoration: none; /* Remove underline */
  color: #333; /* Default text color */
  transition: color 0.3s, background-color 0.3s; /* Smooth hover effect */
}

nav a.router-link-exact-active {
  color: #0077b6; /* Highlight active link */
  font-weight: bold;
}

nav a:hover {
  color: #023e8a; /* Slightly darker hover color */
}

@media (min-width: 1024px) {
  nav {
    text-align: left;
    font-size: 1rem;
  }
}

/* Push the main content down so it doesn't overlap with the header */
RouterView {
  margin-top: 60px; /* Adjust based on header height */
}
</style>
