<template>
  <h1>Edit the book detail</h1>
  <el-row class="inputRow" justify="center" :gutter="20">
    <el-col :span="4"><span class="title ml-3 w-35 text-gray-600 inline-flex items-center">bookID</span></el-col>
    <el-col :span="4">
      <el-input
          v-model="ID"
          disabled
          class="w-50 m-2"/>
    </el-col>
  </el-row>

  <el-row class="inputRow" justify="center" :gutter="20">
    <el-col :span="4"><span class="title ml-3 w-35 text-gray-600 inline-flex items-center">bookName</span></el-col>
    <el-col :span="4">
      <el-input
          v-model="bookNameValue"
          :placeholder="bookNamePlaceholder"
          class="w-50 m-2"/>
    </el-col>
  </el-row>

  <el-row class="inputRow" justify="center" :gutter="20">
    <el-col :span="4"><span class="title ml-3 w-35 text-gray-600 inline-flex items-center">bookType</span></el-col>
    <el-col :span="4">
      <el-select v-model="typeSelectorValue" class="m-2 selector" placeholder="Select">
        <el-option
            v-for="item in typeSelectChoices"
            :key="item.value"
            :label="item.label"
            :value="item.value"
        />
      </el-select>
    </el-col>
  </el-row>

  <el-row class="inputRow" justify="center" :gutter="20">
    <el-col :span="4"><span class="title ml-3 w-35 text-gray-600 inline-flex items-center">bookPrice</span></el-col>
    <el-col :span="4">
      <el-input-number v-model="PriceValue" class="m-2 newRecord"
                       :placeholder="PricePlaceholder"/>
    </el-col>
  </el-row>


  <el-row class="inputRow" justify="center" :gutter="20">
    <el-col :span="4"><span class="title ml-3 w-35 text-gray-600 inline-flex items-center">book_overdue_payment</span>
    </el-col>
    <el-col :span="4">
      <el-input-number v-model="PaymentValue" class="m-2 newRecord"
                       :placeholder="PaymentPlaceholder"/>
    </el-col>
  </el-row>

  <el-row class="inputRow" justify="center" :gutter="20">
    <el-col :span="4"><span class="title ml-3 w-35 text-gray-600 inline-flex items-center">book_category</span></el-col>
    <el-col :span="4">
      <el-input v-model="CategoryValue" class="m-2 newRecord"
                :placeholder="CategoryPlaceholder"/>
    </el-col>
  </el-row>

  <el-row class="inputRow" justify="center" :gutter="20">
    <el-col :span="4"><span class="title ml-3 w-35 text-gray-600 inline-flex items-center">book_snapshot</span></el-col>
    <el-col :span="4">
      <el-input v-model="SnapShotValue" class="m-2 newRecord" maxlength="2000" show-word-limit type="textarea"
                :placeholder="SnapShotPlaceholder"/>
    </el-col>
  </el-row>

  <el-row class="inputRow" justify="center" :gutter="20">
    <el-button @click="resetAll">reset</el-button>
    <el-button @click="submitAll">submit</el-button>
  </el-row>


  <!-- 对话框部分 START -->

  <div>


    <el-dialog v-model="centerDialogVisible" title="Success Tip" width="30%" center>
    <span>
      The detail should been saved!
    </span>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="continueEdit">continue edit</el-button>
        <el-button type="primary" @click="return2HomePage">
          return management page
        </el-button>
      </span>
      </template>
    </el-dialog>
  </div>

</template>

<script setup lang="ts">

import {useRoute, useRouter} from 'vue-router'
import {getCurrentInstance, ref} from "vue";
import axios from "axios";

const router = useRouter();
const route = useRoute();
const ID = ref(route.query.id);
const typeSelectorValue = ref();
let originalType = "";
// global var setting
const {typeSelectChoices, serverPath} = getCurrentInstance().appContext.config.globalProperties;
//let typeSelectorValue = "book";

const bookNameValue = ref();
const bookNamePlaceholder = ref();

const PriceValue = ref();
const PricePlaceholder = ref();

const PaymentValue = ref();
const PaymentPlaceholder = ref();

const CategoryValue = ref();
const CategoryPlaceholder = ref();

const SnapShotValue = ref();
const SnapShotPlaceholder = ref();

axios({
  method: "POST",
  url: serverPath + "bookData/queryBookByID",
  params: {
    id: ID.value,
  }
}).then(res => {
  let tempData = eval("[" + res.data + "]")[0];
  bookNamePlaceholder.value = tempData.name;
  typeSelectorValue.value = tempData.type;
  originalType = tempData.type;
  PricePlaceholder.value = tempData.price;
  PaymentPlaceholder.value = tempData.overdue_payment;
  CategoryPlaceholder.value = tempData.book_category;
  SnapShotPlaceholder.value = tempData.book_snapshot;
});


const resetAll = () => {
  bookNameValue.value = "";
  typeSelectorValue.value = originalType;
  PriceValue.value = "";
  PaymentValue.value = "";
  CategoryValue.value = "";
  SnapShotValue.value = "";
}

const submitAll = () => {
  console.log(bookNameValue.value);
  axios({
    method: "POST",
    url: serverPath + "bookData/updateBook",
    params: {
      id: ID.value,
      name: !bookNameValue.value ? bookNamePlaceholder.value : bookNameValue.value,
      type: typeSelectorValue.value,
      price: !PriceValue.value ? PricePlaceholder.value : PriceValue.value,
      overdue_payment: !PaymentValue.value ? PaymentPlaceholder.value : PaymentValue.value,
      category: !CategoryValue.value ? CategoryPlaceholder.value : CategoryValue.value,
      snapshot: !SnapShotValue.value ? SnapShotPlaceholder.value : SnapShotValue.value,
    }
  });
  centerDialogVisible.value = true;
}

const continueEdit = () => {
  centerDialogVisible.value = false;
  if (bookNameValue.value) {
    bookNamePlaceholder.value = bookNameValue.value;
  }
  originalType = typeSelectorValue.value;
  if (PriceValue.value) {
    PricePlaceholder.value = PriceValue.value;
  }
  if (PaymentValue.value) {
    PaymentPlaceholder.value = PaymentValue.value;
  }
  if (CategoryValue.value) {
    CategoryPlaceholder.value = CategoryValue.value;
  }
  if (SnapShotValue.value) {
    SnapShotPlaceholder.value = SnapShotValue.value;
  }
  resetAll();
}

const return2HomePage = () => {
  centerDialogVisible.value = false;
  router.push({
    name: "book",
  });
}


const centerDialogVisible = ref(false);


/*
alert("Edit succeed!");
  router.push({
    name:"book",
  });

axios({
  method: "POST",
  url: serverPath+"bookData/updateBook",
  params: {
    id: 3,
    name: "VB",
    type: "temp",
    price: 123456,
    overdue_payment: 5000,
    category: "asd",
    snapshot: "teiotheoaishnrf",
  }
});
 */

</script>

<style scoped>
.title {
  vertical-align: middle;
  line-height: 30px;
}

.inputRow {
  padding-top: 20px;
}

.dialog-footer button:first-child {
  margin-right: 10px;
}
</style>