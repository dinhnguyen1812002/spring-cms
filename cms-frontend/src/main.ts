import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router/router'
import ToastService from 'primevue/toastservice';

import PrimeVue from 'primevue/config'
// import Button from 'primevue/button'
// import InputText from 'primevue/inputtext'
// import Textarea from 'primevue/textarea'
// import Checkbox from 'primevue/checkbox'
// import TabView from 'primevue/tabview'
// import TabPanel from 'primevue/tabpanel'
// import Card from 'primevue/card'
// import Message from 'primevue/message'
// import 'primevue/resources/themes/lara-light-indigo/theme.css'
// import 'primevue/resources/primevue.min.css'
// import 'primeicons/primeicons.css'
// import 'primeflex/primeflex.css'

const app = createApp(App)
app.use(ToastService);
app.use(router)
app.use(PrimeVue);
app.mount('#app')
