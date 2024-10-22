import {createApp} from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import router from './router'
import VueParticles from 'vue-particles'
import * as ECharts from 'echarts'

const app = createApp(App);

app.config.globalProperties.$ECharts = ECharts

app.config.globalProperties.typeSelectChoices = [{
    value: 'book', label: 'book',
}, {
    value: 'material', label: 'material',
},]

app.config.globalProperties.userTypeSelectChoices = [{
    value: 'staff', label: 'Library Staff',
}, {
    value: 'patron', label: 'Patron',
},{
    value: 'admin', label: 'Administrator',
},]

app.config.globalProperties.bookCategorySelectChoices = [
    {value: '5', label: 'Aesthetics'},
    {value: '44', label: 'Aeronautics'},
    {value: '47', label: 'Agronomy'},
    {value: '49', label: 'Aquaculture'},
    {value: '21', label: 'Archaeology'},
    {value: '39', label: 'Architecture'},
    {value: '27', label: 'Astronomy'},
    {value: '32', label: 'Biology'},
    {value: '19', label: 'Business'},
    {value: '31', label: 'Chemistry'},
    {value: '50', label: 'Computer Science'},
    {value: '11', label: 'Economics'},
    {value: '14', label: 'Education'},
    {value: '36', label: 'Electric'},
    {value: '42', label: 'Energy'},
    {value: '46', label: 'Engineering'},
    {value: '51', label: 'Environment'},
    {value: '22', label: 'Ethnology'},
    {value: '3', label: 'Ethics'},
    {value: '24', label: 'Finance'},
    {value: '38', label: 'Food science'},
    {value: '48', label: 'Forestry'},
    {value: '28', label: 'Geography'},
    {value: '20', label: 'History'},
    {value: '52', label: 'Information'},
    {value: '17', label: 'Journalism'},
    {value: '13', label: 'Law'},
    {value: '23', label: 'Life'},
    {value: '7', label: 'Linguistics'},
    {value: '8', label: 'Literature'},
    {value: '4', label: 'Logic'},
    {value: '18', label: 'Management'},
    {value: '29', label: 'Mathematics'},
    {value: '33', label: 'Mechanics'},
    {value: '16', label: 'Media'},
    {value: '45', label: 'Medical'},
    {value: '41', label: 'Metallurgy'},
    {value: '12', label: 'Military'},
    {value: '40', label: 'Mining'},
    {value: '1', label: 'Philosophy'},
    {value: '30', label: 'Physics'},
    {value: '10', label: 'Politics'},
    {value: '6', label: 'Psychology'},
    {value: '2', label: 'Religion'},
    {value: '26', label: 'Sociology'},
    {value: '15', label: 'Sports'},
    {value: '25', label: 'Statistics'},
    {value: '34', label: 'Telecommunications'},
    {value: '37', label: 'Textiles'},
    {value: '43', label: 'Transportation'},
    {value: '35', label: 'Water conservancy'}
];


app.config.globalProperties.serverPath = "http://101.201.143.17:8082/"
//app.config.globalProperties.serverPath = "http://localhost:8082/"

app.use(VueParticles).use(ElementPlus).use(router).mount('#app')

router.beforeEach((to, from, next) => {
    if (to.meta.requireAuth) {
        if (localStorage.userName) {
            next();
        } else {
            next({
                path: '/',
                query: {
                    redirect: to.fullPath
                }
            })
        }
    } else {
        next();
    }
})