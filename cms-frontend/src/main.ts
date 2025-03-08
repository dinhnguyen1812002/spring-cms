import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router/router'
import ToastService from 'primevue/toastservice';
import PrimeVue from 'primevue/config';

const app = createApp(App) 
app.use(ToastService);
app.use(router)
app.use(PrimeVue);
app.mount('#app')
