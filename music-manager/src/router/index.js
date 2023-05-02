import Vue from 'vue'
import VueRouter from "vue-router"
import index from '../views/index/index.vue'

Vue.use(VueRouter)

export const routes = [
  {
    path: '/',
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
        path:'/test',
        name:'test',
        component:()=>import('../views/test/player.vue'),
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
  // {
  //   path: '/',
  //   name: 'index',
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () => import(/* webpackChunkName: "about" */ '../views/index/index.vue')
  // }
]

// const router = createRouter({
//   history: createWebHistory(process.env.BASE_URL),
//   mode:'hash',
//   routes
// })

export default new VueRouter({
  //   mode:'hash',
  mode: 'history', // 去掉url中的#
  scrollBehavior: () => ({ y: 0 }),
  routes
})
