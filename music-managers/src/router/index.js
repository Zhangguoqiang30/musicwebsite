import { createRouter, createWebHistory } from 'vue-router'
import index from '../views/index/index.vue'

const routes = [
  {
    path: '/',
    name: 'index',
    component: index,
    children:[{
      // path:'/homepage',
      path:'/',
      name:'homepage',
      component:()=>import('../views/homepage/homepage.vue'),
    },
      {
        path:'/usermanager',
        name:'usermanager',
        component:()=>import('../views/usermanager/usermanager.vue'),
      },
      {
        path:'/singermanager',
        name:'singermanager',
        component:()=>import('../views/singermanager/singermanager.vue'),
      },
      {
        path:'/listmanager',
        name:'listmanager',
        component:()=>import('../views/listmanager/listmanager.vue'),
      }]
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  mode:'hash',
  routes
})

export default router
