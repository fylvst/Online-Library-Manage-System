<template>
  <div>
    <div class="searchPanel">
      <el-row align="middle">
        <el-col>
          <el-button @click="addingABook" type="success" style="margin-right: 2%">add a new staff account</el-button>
        </el-col>

      </el-row>

      <div>
        <admin_staff-data_table style="margin: 0 auto;" v-model="tableData"></admin_staff-data_table>
      </div>

    </div>
    <div>
      <admin_staff-adding_popout-window v-model="userAddingModel"></admin_staff-adding_popout-window>
    </div>
  </div>


</template>

<script lang='ts' setup>
import {getCurrentInstance, ref, watch} from 'vue'
import {useRouter} from "vue-router";
import axios from "axios";
import {Search} from '@element-plus/icons-vue'
import BookDataTableWithPagination from "@/components/staff_bookData_table.vue";
import BookAddingWindow from "@/components/staff_bookAddingWindow.vue";
import {ElMessage} from "element-plus";
import Admin_patronData_table from "@/components/admin_patronData_table.vue";
import Admin_patronAdding_popoutWindow from "@/components/admin_patronAdding_popoutWindow.vue";
import Admin_staffData_table from "@/components/admin_staffData_table.vue";
import Admin_staffAdding_popoutWindow from "@/components/admin_staffAdding_popoutWindow.vue";
/*
axios({
    method: "POST",
    url: serverPath + "bookData/deleteByID",
    params: {
      id: eval(scope.row.ID),
    }
  });

 router.push({
    name: 'bookDetail',
    query: {
      id: Data.ID,
    }
  })

  const router = useRouter();
  const {typeSelectChoices, serverPath} = getCurrentInstance().appContext.config.globalProperties;//全局变量获取
*/
const {
  serverPath,
} = getCurrentInstance().appContext.config.globalProperties;//全局变量获取

const userAddingModel = ref({
  editInfoVisi: false,
  newData: false,
})


let testUserData = [
  {
    "isDelete": null,
    "createTime": null,
    "updateTime": null,
    "username": "zbq",
    "password": null,
    "nickname": "hhh",
    "age": 12,
    "phone": "13619185732",
    "email": "100@qq.com",
    "sid": 1
  },
];
const rawData = ref();
const tableData = ref(testUserData);


const QueryAllUser = async () => {
  tableData.value.length = 0;
  let tempResult: any;
  axios({
    method: "GET",
    url: serverPath + "admin/listStaff"
  }).then(res => {
    tempResult = res.data;
    for (let i = 0; i < tempResult.data.length; i++) {
      tableData.value.push(tempResult.data[i]);
    }
    rawData.value = tableData.value.slice();
  });

}

QueryAllUser();

const addingABook = () => {
  userAddingModel.value.editInfoVisi = true;
}

watch(
    userAddingModel.value,
    (newval, oldval) => {
     QueryAllUser();
    },
);

</script>

<style scoped>
</style>