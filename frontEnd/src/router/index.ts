import {createRouter, createWebHashHistory, createWebHistory, RouteRecordRaw} from 'vue-router'
import HomeView from '../views/HomeView.vue'

const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        name: 'home',
        //component: HomeView,
        // redirect: {name: "testView"},
        redirect: {name: "login"},
    },
    {
        path: '/about',
        name: 'about',
        component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
    },
    {
        path: '/book',
        name: 'book',
        component: () => import(/* webpackChunkName: "about" */ '../views/bookManagementView.vue')
    }, {
        path: '/bookDetail',
        name: 'bookDetail',
        component: () => import(/* webpackChunkName: "about" */ '../views/bookDetailView.vue')
    }, {
        path: '/bookLending',
        name: 'lendingBook',
        component: () => import(/* webpackChunkName: "about" */ '../views/BookLendingManagementView.vue')
    }, {
        path: '/lendRecordDetail',
        name: 'lendRecordDetail',
        component: () => import(/* webpackChunkName: "about" */ '../views/bookLendingDetailView.vue')
    }, {
        path: '/patronMainView',
        name: 'patronMainView',
        component: () => import(/* webpackChunkName: "about" */ '../views/patron_Main_View.vue'),
        children: [
            {
                path: '',
                name: 'patron_default',
                redirect: {name: "patron_bookTable"}
            },
            {
                path: 'bookTable',
                name: 'patron_bookTable',
                component: () => import('../views/patron_bookTable_View.vue'),


            }, {
                path: 'bookLending',
                name: 'patron_bookLending',
                component: () => import('../views/patron_bookLending_View.vue'),
            }, {
                path: 'bookAttention',
                name: 'patron_bookAttention',
                component: () => import('../views/patron_bookAttention_View.vue'),
            },{
                path: 'dashboard',
                name: 'patron_dashboard',
                component: () => import('../views/dashBoard.vue'),
            }
        ],

    }, {
        path: '/staffMainView',
        name: 'staffMainView',
        component: () => import(/* webpackChunkName: "about" */ '../views/staff_Main_View.vue'),
        children: [
            {
                path: 'bookTable',
                name: 'staff_bookTable',
                component: () => import('../views/staff_bookTable_View.vue'),
            }, {
                path: 'bookLending',
                name: 'staff_bookLending',
                component: () => import('../views/staff_bookLending_View.vue'),
            }, {
                path: 'cateManagement',
                name: 'staff_cateManagement',
                component: () => import('../views/staff_cateManagement_View.vue'),
            },{
                path: 'dashboard',
                name: 'staff_dashboard',
                component: () => import('../views/dashBoard.vue'),
            }
        ],

    }
    , {
        path: '/adminMainView',
        name: 'adminMainView',
        component: () => import(/* webpackChunkName: "about" */ '../views/admin_Main_View.vue'),
        children: [
            {
                path: 'patronManagement',
                name: 'admin_patronManagement',
                component: () => import('../views/admin_patronManagement_View.vue'),
            },{
                path: 'staffManagement',
                name: 'admin_staffManagement',
                component: () => import('../views/admin_staffManagement_View.vue'),
            },{
                path: 'dashboard',
                name: 'admin_dashboard',
                component: () => import('../views/dashBoard.vue'),
            }
        ],

    }
    , {
        path: '/login',
        name: 'login',
        component: () => import(/* webpackChunkName: "about" */ '../views/loginView.vue')
    }, {
        path: '/testView',
        name: 'testView',
        component: HomeView,
        children: [
            {
                path: 'child1',
                name: 'child1',
                component: () => import('../views/patron_bookLending_View.vue'),


            }, {
                path: 'child2',
                name: 'child2',
                component: () => import('../views/patron_bookLending_View.vue'),
            },
        ],
    }

]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

router.beforeEach((to, from, next) => {
    if (to.path == '/login') {
        next();
    } else {
        if (from.path == "/login")//从登录页面可以直接通过登录进入主页面
        {
            next();
        } else {
            //从/进入,如果登录状态是true，则直接next进入主页面
            if (localStorage.pid || localStorage.sid || localStorage.adminUserName) {
                next();
            } else {//如果登录状态是false，那么跳转至登录页面,需要登录才能进入主页面
                next('/login');
            }
        }
    }

});

export default router;
