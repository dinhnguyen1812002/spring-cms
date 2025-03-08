import { createRouter, createWebHistory, type RouteRecordRaw, } from "vue-router";
import Home from "../views/Home.vue";
import EditPage from "../views/EditPage.vue";
import CreatePage from "../views/CreatePage.vue";
import List from "../views/List.vue";


const routes: RouteRecordRaw[] = [
  { path: "/", component: Home},
  { path: "/edit/:slug", component: EditPage },
  {path:"/list" ,name :"list", component:List },
  { path: "/create", component: CreatePage}
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
{}