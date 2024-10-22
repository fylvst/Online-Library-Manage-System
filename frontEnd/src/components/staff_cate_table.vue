<template>


  <div class="tablePart">
    <el-table
        stripe
        :border="true"
        :data="tableData"
        :height="tableHeight"
    >
      <el-table-column align="center" type="index"/>
      <el-table-column prop="cid" align="center" label="id"/>
      <el-table-column prop="cateName" align="center" label="category name"/>
      <!--      <el-table-column prop="description" label="description" width="1200"/> -->
      <el-table-column align="center" label="Operations">
        <template #default="scope">
          <el-button link type="danger" size="small" @click="deteleACate(scope)">delete</el-button>
          <el-button link type="primary" size="small" @click="updateACate(scope)">update</el-button>
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
  <staff_cate-update_popout-window :model-value="cateUpdateData"></staff_cate-update_popout-window>

  <el-dialog
      v-model="deleteConfirmWindow"
      title="Warning"
      width="30%"
      destroy-on-close
      center
  >
    <span>
      Are you sure you want to delete catagory <strong style="color: red"> {{ deletingCateInfo.cateName }}</strong>
    </span>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="centerDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="submitDelete">
          Confirm
        </el-button>
      </span>
    </template>
  </el-dialog>

</template>


<script lang='ts' setup>
import {getCurrentInstance, ref, watch} from 'vue'
import {useRouter} from "vue-router";
import Staff_cateUpdate_popoutWindow from "@/components/staff_cateUpdate_popoutWindow.vue";
import axios from "axios";
import {ElMessage} from "element-plus";

// eslint-disable-next-line no-undef
const props = defineProps<{
  modelValue: any;
}>();

const router = useRouter();
const {serverPath} = getCurrentInstance().appContext.config.globalProperties;//全局变量获取
const cateUpdateData = ref({
  editInfoVisi: false,
  succeed: false,
  data: {
    cid: 1,
    newName: "test",
  },
});
const tempScope = ref();

const rawData = ref(props.modelValue);
const tableData = ref(rawData.value);
const currentPage = ref(1);
const pageSize = ref(30);
const deleteConfirmWindow = ref(false);
const deletingCateInfo = ref();
const tableHeight = ref(document.documentElement.clientHeight * 0.7);

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

watch(props.modelValue, (newVal, oldVal) => {
  tableDataRefreash();
})

const updateACate = (scope: any) => {
  let data = scope.row;
  tempScope.value = scope;
  cateUpdateData.value.editInfoVisi = true;
  cateUpdateData.value.data = scope.row;
}

watch(cateUpdateData.value, async (newVal, oldVal) => {
  if (cateUpdateData.value.succeed == true) {
    for (let i = 0; i < rawData.value.length; i++) {
      if (rawData.value[i].cid == cateUpdateData.value.data.cid) {
        rawData.value[i].cateName = cateUpdateData.value.data.newName;
        break;
      }
    }
    cateUpdateData.value.succeed = false;
  }
})


const deteleACate = (scope: any) => {
  deleteConfirmWindow.value = true;
  deletingCateInfo.value = scope.row;
}

const submitDelete = () => {
  axios({
    method: "DELETE",
    url: serverPath + "category/del",
    params: {
      cid: deletingCateInfo.value.cid,
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
        if (rawData.value[i].cid == deletingCateInfo.value.cid)
          break;
      rawData.value.splice(i, 1);
    } else {
      ElMessage({
        showClose: true,
        message: 'Oops, error.',
        type: 'error',
      });
    }
    deleteConfirmWindow.value = false;
  });

}


</script>

<style scoped>


</style>