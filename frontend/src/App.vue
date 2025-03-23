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
        <RouterLink to="/flights">flights</RouterLink>
        <RouterLink to="/admin">admin</RouterLink>
      </nav>
    </div>
  </header>

  <RouterView />
</template>

<style scoped>
header {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  background-color: #00b4d8;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  z-index: 1000;
  line-height: 1.5;
}

.wrapper {
  max-width: 1200px;
  margin: 0 auto;
  padding: 1rem;
}

nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.nav-links {
  display: flex;
  gap: 1rem;
}

.user-icon i {
  font-size: 1.5rem;
  color: #333;
  transition: color 0.3s;
}

.user-icon:hover i {
  color: #023e8a;
}

nav a {
  display: inline-block;
  padding: 0.5rem 1rem;
  text-decoration: none;
  color: #333;
  transition: color 0.3s, background-color 0.3s;
}

nav a.router-link-exact-active {
  color: #0077b6;
  font-weight: bold;
}

nav a:hover {
  color: #023e8a;
}

@media (min-width: 1024px) {
  nav {
    text-align: left;
    font-size: 1rem;
  }
}

RouterView {
  margin-top: 60px;
}
</style>
