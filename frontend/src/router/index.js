import { createRouter, createWebHistory } from 'vue-router'
import AdminPage from "../components/AdminPage.vue";
import flights from "@/components/Flights.vue";
import FlightDetail from "@/components/FlightDetail.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/admin',
            name: 'admin',
            component: AdminPage,
        },
        {
            path: '/flights',
            name: 'flights',
            component: flights,
        },
        {
            path: '/flight/:flightId',
            name: 'flight-detail',
            component: FlightDetail,
            props: true
        }
    ],
})

export default router
