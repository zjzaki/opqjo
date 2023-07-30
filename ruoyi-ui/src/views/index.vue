<template>
  <div class="app-container home">
    <panel-group @handleSetLineChartData="handleSetLineChartData" @getData="getData"/>
    <!--折线图-->
    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <line-chart :chart-data="lineChartData"/>
    </el-row>

    <el-row :gutter="32">
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <raddar-chart/>
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <pie-chart/>
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <bar-chart/>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import PanelGroup from './dashboard/PanelGroup'
import LineChart from './dashboard/LineChart'
import RaddarChart from './dashboard/RaddarChart'
import PieChart from './dashboard/PieChart'
import BarChart from './dashboard/BarChart'

const lineChartData = {
  dataZoom: [
    {
      type: 'slider',
      show: true,
      start: 0,
      end: 100,
      handleSize: 8
    },
    {
      type: 'inside',
      start: 94,
      end: 100,
      zoomOnMouseWheel: 'ctrl'
    },
  ],
  xAxis: {
    data: [],
    boundaryGap: true,
    axisTick: {
      show: false
    },
    axisLabel: {
      interval: 0,
      width: 100,
      overflow: 'breakAll'
    }
  },
  grid: {
    left: 10,
    right: 10,
    bottom: 50,
    top: 30,
    containLabel: true
  },
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'cross'
    },
    padding: [5, 10]
  },
  yAxis: {
    axisTick: {
      show: false
    }
  },
  legend: {
    data: ['今日消息数', '总消息数']
  },
  series: [{
    name: '今日消息数',
    label: {
      normal: {
        fontSize: 15,
        show: true,
        position: "top",
        color: '#3cfd0b'
      }
    },
    itemStyle: {
      normal: {
        color: '#3fde1b',
        lineStyle: {
          color: '#40f511',
          width: 2
        }
      }
    },
    smooth: true,
    type: 'line',
    data: [],
    animationDuration: 2800,
    animationEasing: 'cubicInOut'
  },
    {
      name: '总消息数',
      smooth: true,
      type: 'line',
      label: {
        normal: {
          fontSize: 15,
          show: true,
          position: "top",
          color: '#3888fa'
        }
      },
      itemStyle: {
        normal: {
          color: '#3888fa',
          lineStyle: {
            color: '#3888fa',
            width: 2
          },
          areaStyle: {
            color: '#f3f8ff'
          }
        }
      },
      data: [],
      animationDuration: 2800,
      animationEasing: 'quadraticOut'
    }]
}

export default {
  name: 'Index',
  components: {
    PanelGroup,
    LineChart,
    RaddarChart,
    PieChart,
    BarChart
  },
  data() {
    return {
      lineChartData: lineChartData,
      xAxisData: [],
      todayMsgCount: [],
      msgCount: []
    }
  },
  methods: {
    handleSetLineChartData(type) {

    },
    getData(data) {
      //数据拼装
      var dataArr = data.opqStatisticGroupMsgList;
      //遍历列表
      for (let i = 0; i < dataArr.length; i++) {
        //得到当前index的值
        let itemData = dataArr[i];
        //x轴
        this.xAxisData.push(itemData.groupName);
        //今日发言数
        this.todayMsgCount.push(itemData.todayMsgCount);
        //总消息数
        this.msgCount.push(itemData.msgCount);
      }
      //设置折线图数据
      lineChartData.xAxis.data = this.xAxisData;
      lineChartData.series[0].data = this.todayMsgCount;
      lineChartData.series[1].data = this.msgCount;
    }
  }
};
</script>

<style lang="scss" scoped>
.app-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width: 1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>

