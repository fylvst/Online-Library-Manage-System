<template>
  <div class="home">


    <el-row class="row-bg" justify="space-evenly">
      <el-col :span="6">
        <div ref="bookAmount" class="charts"></div>
      </el-col>
      <el-col :span="6">
        <div ref="pieChart" class="charts"></div>
      </el-col>
      <el-col :span="6">
        <div ref="barChart" class="charts"></div>
      </el-col>
    </el-row>

  </div>
</template>

<script setup lang="ts">

import * as echarts from 'echarts';
import {onMounted, ref, getCurrentInstance} from 'vue';
import axios from "axios";

const {serverPath} = getCurrentInstance().appContext.config.globalProperties;//全局变量获取


const getDay = (day: any) => {
  var today = new Date();
  var targetday_milliseconds = today.getTime() + 1000 * 60 * 60 * 24 * day;
  today.setTime(targetday_milliseconds); //注意，这行是关键代码
  //var tYear = today.getFullYear();
  var tMonth = today.getMonth() + 1;
  var tDate = today.getDate();
  return tMonth + "-" + tDate;
}

const barChart = ref<HTMLElement>();
const myChart1 = ref<any>();

// 绘制柱形图
function initBarEcharts(nowData: any) {
  myChart1.value = echarts.init(barChart.value!);
  let nowDate = new Date();
  //nowDate -= (24 * 3600 * 1000 * 1);
  myChart1.value.setOption({
    title: {
      text: 'Library Total Income',
      x: 'center'
    },
    xAxis: {
      type: 'category',
      data: [getDay(-6), getDay(-5), getDay(-4), getDay(-3), getDay(-2), getDay(-1), getDay(0)]
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        data: [1120, 1156, 1175, 1214, 1259, 1300, nowData],
        type: 'line'
      }
    ],
    tooltip: { // 鼠标悬浮提示框显示 X和Y 轴数据
      trigger: 'item',
      formatter: ' {c}',

    }

  });
}


const pieChart = ref<HTMLElement>();
const bookAmount = ref<HTMLElement>();


function initPieEcharts(pieChart: any, Data: any, title: any) {
  const myChart2 = ref<any>();
  myChart2.value = echarts.init(pieChart.value!);
  let nowDate = new Date();
  //nowDate -= (24 * 3600 * 1000 * 1);
  myChart2.value.setOption({
    tooltip: {
      trigger: 'item'
    },
    title: {
      text: title,
      x: 'center'
    }
    ,
    legend: {
      top: '5%',
      left: 'center'
    },
    series: [
      {
        type: 'pie',
        radius: ['40%', '70%'],
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 10,
          borderColor: '#fff',
          borderWidth: 2
        },
        label: {
          show: false,
          position: 'center'
        },
        emphasis: {
          label: {
            show: true,
            fontSize: 40,
            fontWeight: 'bold'
          }
        },
        labelLine: {
          show: false
        },
        data: Data,
      }
    ]
  });
}

function initPieEcharts2(pieChart: any, Data: any, title: any) {
  const myChart2 = ref<any>();
  myChart2.value = echarts.init(pieChart.value!);
  let nowDate = new Date();
  //nowDate -= (24 * 3600 * 1000 * 1);
  myChart2.value.setOption({
    title: {
      text: title,
      left: 'center'
    },
    tooltip: {
      trigger: 'item'
    },
    legend: {
      orient: 'vertical',
      left: 'left'
    },
    series: [
      {
        name: 'Access From',
        type: 'pie',
        radius: '50%',
        data: Data,
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  });
}

onMounted(() => {
  axios({
    method: "GET",
    url: serverPath + "getDash",
    headers: {
      Origin: serverPath,
      Referer: serverPath,
    },
  }).then(res => {
    let rawData = res.data.data;

    initBarEcharts(rawData.money);
    initPieEcharts(pieChart,
        [{value: rawData.patronNum, name: 'patron'}, {value: rawData.staffNum, name: 'staff'}],
        'Library Register Amount');
    initPieEcharts2(bookAmount,
        [{value: rawData.bookAvail, name: 'available'}, {
          value: rawData.bookNum - rawData.bookAvail
          , name: 'lend'
        }],
        'Book Amount');
    console.log(rawData);
  })

});

</script>
<style>
.charts {
  width: 350px;
  height: 400px;
}


</style>