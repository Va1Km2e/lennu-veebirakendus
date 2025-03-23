import { createRouter, createWebHistory } from 'vue-router'
import AdminPage from "../components/AdminPage.vue";
import HelloWorld from "../components/HelloWorld.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'home',
            component: HelloWorld,
        },
        {
            path: '/admin',
            name: 'admin',
            component: AdminPage,
        }
    ],
})

export default router
