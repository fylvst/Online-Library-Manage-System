<template>
  <div class="home">

    <div ref="barChart" style="width: 1000px;height: 500px">
    </div>


  </div>
</template>

<script setup lang="ts">

import * as echarts from 'echarts';
import { onMounted, ref } from 'vue';
const barChart = ref<HTMLElement>();
const myChart1 = ref<any>();
// 绘制柱形图
function initBarEcharts() {
  myChart1.value = echarts.init(barChart.value!);
  myChart1.value.setOption({
    title: {
      text: '学习输出',
      x: 'center'
    },
    tooltip: {
      trigger: 'item'
    },
    legend: {
      data: ['输出量', '输入量'],
      orient: 'vertical',
      right: 60,
      top: 20
    },
    xAxis: {
      data: ['六月', '七月', '八月', '九月', '十月']
    },
    yAxis: {},
    color: ['#c38bef', '#6dbcf7'],
    series: [
      {
        name: '输出量',
        type: 'line',
        data: [2, 9, 6, 3, 1],
        smooth: true,
        label: {
          show: true,
          position: 'top'
        }
      },
      {
        name: '输入量',
        type: 'bar',
        data: [9, 18, 12, 9, 6],
        barWidth: '20',
        label: {
          show: true,
          position: 'top'
        }
      }
    ]
  });
}

onMounted(() => {
  initBarEcharts();
});

</script>
<style>

.fade-right-enter-to,
.fade-right-leave-from {
  opacity: 1;
  transform: none;
}

.fade-right-enter-active,
.fade-right-leave-active {
  transition: all 0.5s;
}


.fade-right-enter-from,
.fade-right-leave-to {
  opacity: 0;
  transform: translateX(20px);
}

#container{
  background-color: red;
}

</style>