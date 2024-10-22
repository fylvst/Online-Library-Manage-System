<template>

  <!-- book selector START-->
  <div>
    <el-select v-model="typeSelectorValue" class="m-2 selector" placeholder="Select"
               @change="selectOnchangeEvent('type', typeSelectorValue )">
      <el-option
          v-for="item in typeSelectChoices"
          :key="item.value"
          :label="item.label"
          :value="item.value"
      />
    </el-select>

  </div>


  <!-- book selector START-->


  <!-- book information prohibit START -->


  <el-table :data="tableData" stripe border>
    <el-table-column prop="ID" label="ID" sortable/>
    <el-table-column prop="name" label="Name" sortable/>
    <el-table-column prop="type" label="Type" sortable/>
    <el-table-column prop="price" label="Price" sortable/>
    <el-table-column prop="overdue_payment" label="Overdue payment" sortable/>
    <el-table-column prop="book_category" label="Category" sortable/>

    <el-table-column label="Operations">
      <template #default="scope">
        <el-button
            link
            type="primary"
            size="small"
            @click.prevent="editButtonJumper(scope)"
        >
          Edit
        </el-button>
        <el-button
            link
            type="danger"
            size="small"
            @click.prevent="deleteRow(scope)"
        >
          Remove
        </el-button>
      </template>
    </el-table-column>
  </el-table>

  <!-- book information prohibit END -->

  <!-- book adding window setup START -->
  <el-button text @click="centerDialogVisible = true">
    add a new record
  </el-button>

  <el-dialog
      v-model="centerDialogVisible"
      title="input the new record"
      width="40%"
      destroy-on-close
      center
  >

    <div>
      <el-input v-model="newNameInput" class="m-2 newRecord" required="required" placeholder="Please input the name"/>

      <el-select v-model="newTypeSelectValue" class=" newRecord" required="required" placeholder="Select">
        <el-option
            v-for="item in typeSelectChoices"
            :key="item.value"
            :label="item.label"
            :value="item.value"
        />
      </el-select>

      <el-input-number v-model="newPriceInput" class="m-2 newRecord" required="required"
                       placeholder="Please input the price"/>

      <el-input-number v-model="new_overdue_payment" class="m-2 newRecord" required="required"
                       placeholder="Please input the overdue_payment"/>

      <el-input v-model="newCategoryInput" class="m-2 newRecord" required="required"
                placeholder="Please input the book_category"/>

      <el-input v-model="newSnapshotInput" class="m-2 newRecord" maxlength="2000" show-word-limit type="textarea"
                required="required" placeholder="Please input the book_snapshot"/>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="deleteAlreadyInput">Cancel</el-button>
        <el-button type="primary" @click="addingANewRecord">
          Confirm
        </el-button>
      </span>
    </template>
  </el-dialog>
  <!-- book adding window setup END -->
</template>

<script lang="ts" setup>
import {getCurrentInstance, ref} from 'vue'
import {useRouter} from "vue-router";
import axios from "axios";
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

const router = useRouter();
const {typeSelectChoices, serverPath} = getCurrentInstance().appContext.config.globalProperties;//全局变量获取


let rawData: any//store the original data
const tableData = ref([//store the data showed in the browser table
  {
    ID: 'test',
    name: 'test',
    type: 'test',
    price: 5000,
    overdue_payment: 200,
    book_category: "aaa",
    book_snapshot: "it's a book"
  },
])

axios({
  method: "POST",
  url: serverPath + "bookData/queryAllBookData"
}).then(res => {
  tableData.value = eval(res.data);
  rawData = eval(res.data);
});

const deleteRow = (scope: any) => {
  axios({
    method: "POST",
    url: serverPath + "bookData/deleteByID",
    params: {
      id: eval(scope.row.ID),
    }
  });
  tableData.value.splice(scope.$index, 1)
  rawData = tableData.value;
}

const deleteAlreadyInput = () => {
  centerDialogVisible.value = false;
  newNameInput.value = '';
  newTypeSelectValue.value = '';
}

const editButtonJumper = (scope: any) => {
  let Data = scope.row;
  router.push({
    name: 'bookDetail',
    query: {
      id: Data.ID,
    }
  })
}

const addingANewRecord = () => {
  let Name = newNameInput.value;
  let Type = newTypeSelectValue.value;
  let Price = newPriceInput.value;
  let Payment = new_overdue_payment.value;
  let Category = newCategoryInput.value;
  let SnapShot = newSnapshotInput.value;
  if (Type == '' || Name == '') {
    alert("Wrong input!");
    return;
  }
  tableData.value.push({
    ID: (eval(rawData[rawData.length - 1]["ID"]) + 1).toString(),
    name: Name,
    type: Type,
    price: eval(Price),
    overdue_payment: eval(Payment),
    book_category: Category,
    book_snapshot: SnapShot,
  })
  axios({
    method: "POST",
    url: serverPath + "bookData/addNewBook",
    params: {
      "name": Name,
      "type": Type,
      "price": Price,
      "overdue_payment": Payment,
      "category": Category,
      "snapshot": SnapShot,
    }
  });
  rawData = tableData.value;
  deleteAlreadyInput();

}


const centerDialogVisible = ref(false)
const newNameInput = ref('')
const newPriceInput = ref('')
const new_overdue_payment = ref('')
const newCategoryInput = ref('')
const newTypeSelectValue = ref('')
const newSnapshotInput = ref('')

const getUniqueChoice = (key: string, rawData: any): any => {
  let temp = new Set();
  for (let i = 0; i < rawData.length; i++) {
    temp.add(rawData[i][key]);
  }
  let arraylist = Array.from(temp);
  let result = [];
  for (let i = 0; i < arraylist.length; i++) {
    result.push({
      value: arraylist[i], label: arraylist[i]
    })
  }
}

const typeSelectorValue = ref();


const selectOnchangeEvent = (key: string, value: string) => {
  let temp: any[] = [];
  rawData.forEach((item: any) => {
    if (eval("item." + key) == value) {
      temp.push(item);
    }
  })
  tableData.value = temp;
}
</script>

<style scoped>
.newRecord {
  margin-top: 20px;
  margin-left: auto;
  margin-right: auto;
  width: 100%;
}
</style>