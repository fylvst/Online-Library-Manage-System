<template>
  <div class="container">
    <div class="tablePart">
      <el-table
          stripe
          :border="true"
          :data="tableData"
          :height="tableHeight"
      >
        <el-table-column type="expand">
          <template #default="props">


            <div style="margin:0  5%">
              <p m="t-0 b-2">phone: {{ props.row.phone }}</p>
              <p m="t-0 b-2">email: {{ props.row.email }}</p>

            </div>
          </template>
        </el-table-column>
        <el-table-column align="center" type="index"/>
        <el-table-column prop="sid" align="center" label="id"/>
        <el-table-column prop="nickname" align="center" label="nickname"/>
        <el-table-column prop="username" align="center" label="username"/>
        <el-table-column align="center" label="Operations">
          <template #default="scope">
            <el-button link type="danger" size="small" @click="deleteUser(scope)">Delete</el-button>
            <el-button link type="primary" size="small" @click="editUser(scope)">Edit</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="pagePart" style="margin-top: 10px">
      <el-row justify="center">
        <el-pagination
            v-model:current-page="currentPage"
            v-model:page-size="pageSize"
            :page-sizes="[10, 20, 30, 40,50,60]"
            :small="false"
            :disabled="false"
            :background="true"
            layout="total, sizes, prev, pager, next, jumper"
            :total="rawData.length"
            @size-change="tableDataRefreash"
            @current-change="tableDataRefreash"
        />
      </el-row>
    </div>

    <el-dialog
        v-model="deleteConfirm"
        title="Warning"
        width="30%"
        destroy-on-close
        center
    >
    <span>
      Are you sure you want to delete staff <strong style="color: red"> {{ deleteInfo.userName }}</strong>
    </span>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="deleteConfirm = false">Cancel</el-button>
        <el-button type="primary" @click="submitDelete">
          Confirm
        </el-button>
      </span>
      </template>
    </el-dialog>


  </div>
  <base-user-information-change :model-value="editInfo"></base-user-information-change>
</template>


<script lang='ts' setup>
import {getCurrentInstance, ref, watch} from 'vue'
import {useRouter} from "vue-router";
import axios from "axios";
import {ElMessage} from "element-plus";
import BaseUserInformationChange from "@/components/baseUserInformationChange.vue";

// eslint-disable-next-line no-undef
const props = defineProps<{
  modelValue: any;
}>();

const router = useRouter();
const {serverPath} = getCurrentInstance().appContext.config.globalProperties;//全局变量获取


console.log(props.modelValue);
const rawData = ref(props.modelValue);
const tableData = ref(rawData.value);
const currentPage = ref(1);
const pageSize = ref(30);
const tableHeight = ref(document.documentElement.clientHeight * 0.73);
const deleteConfirm = ref(false);
const deleteInfo = ref({
  userName: "test",
  sid: 0,
})

const editInfo = ref({
  succeedAdding: false,
  editInfoVisi: false,
  sid: 0,
  userType: "patron",
  isDelete: null,
  createTime: null,
  updateTime: null,
  username: "zbq",
  password: null,
  nickname: "hhh",
  age: 12,
  phone: "13619185732",
  email: "100@qq.com",
})

const tableDataRefreash = () => {
  //rawData.value.reverse();
  let start = pageSize.value * (currentPage.value - 1);
  let end = start + pageSize.value;
  end = end > rawData.value.length ? rawData.value.length : end;
  let tempTable = [];
  while (start < end) {
    tempTable.push(rawData.value[start]);
    start += 1;
  }
  tableData.value = tempTable;
}

tableDataRefreash();

const deleteUser = (scope: any) => {
  deleteConfirm.value = true;
  deleteInfo.value.sid = scope.row.sid;
  deleteInfo.value.userName = scope.row.username;
}

const submitDelete = () => {
  axios({
    method: "POST",
    url: serverPath + "admin/delStaff",
    params: {
      sid: deleteInfo.value.sid,
    }
  }).then(res => {
    if (res.data.code == 200) {
      ElMessage({
        showClose: true,
        message: 'Deleting succeed!',
        type: 'success',
      });
      let i = 0;
      for (i = 0; i < rawData.value.length; i++)
        if (rawData.value[i].sid == deleteInfo.value.sid)
          break;
      rawData.value.splice(i, 1);
    } else {
      ElMessage({
        showClose: true,
        message: 'Oops, error.',
        type: 'error',
      });
    }
    deleteConfirm.value = false;
  });
}


watch(props.modelValue, (newVal, oldVal) => {
  tableDataRefreash();
})


const editUser = (scope: any) => {
  axios({
    method: "POST",
    url: serverPath + "staff/getBySid",
    params: {
      sid: scope.row.sid
    }
  }).then(res => {
    editInfo.value = res.data.data;
    editInfo.value.sid = scope.row.sid;
    editInfo.value.userType = "staff";
    editInfo.value.editInfoVisi = true;
    editInfo.value.succeedAdding = false;
  });
}

watch(()=>{return editInfo.value.succeedAdding},async (newVal, oldVal) => {
  if (editInfo.value.succeedAdding) {
    for (let i = 0; i < rawData.value.length; i++) {

      if (rawData.value[i].sid == editInfo.value.sid) {
       await axios({
         method: "POST",
         url: serverPath + "staff/getBySid",
         params: {
           sid: editInfo.value.sid
         }
       }).then(res => {
         let receiveData = res.data.data;
         rawData.value[i].nickname = receiveData.nickname;
         rawData.value[i].username = receiveData.username;
         rawData.value[i].phone = receiveData.phone;
         rawData.value[i].email = receiveData.email;
         rawData.value[i].age = receiveData.age;

       });
        break;
      }
    }
    editInfo.value.succeedAdding = false;
  }
})

</script>

<style scoped>

</style>