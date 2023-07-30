<template>
  <el-row :gutter="40" class="panel-group">
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('newVisitis')">
        <div class="card-panel-icon-wrapper icon-peoples">
          <svg-icon icon-class="peoples" class-name="card-panel-icon"/>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">活跃群组</div>
          <count-to :start-val="0" :end-val="peoples" :duration="2600" class="card-panel-num"/>
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('purchases')">
        <div class="card-panel-icon-wrapper icon-people">
          <svg-icon icon-class="people" class-name="card-panel-icon"/>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">预留</div>
          <count-to :start-val="0" :end-val="0" :duration="3200" class="card-panel-num"/>
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('messages')">
        <div class="card-panel-icon-wrapper icon-message">
          <svg-icon icon-class="message" class-name="card-panel-icon"/>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">今日消息</div>
          <count-to :start-val="0" :end-val="message" :duration="3000" class="card-panel-num"/>
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('shoppings')">
        <div class="card-panel-icon-wrapper icon-messageCount">
          <svg-icon icon-class="chart" class-name="card-panel-icon"/>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">总消息数</div>
          <count-to :start-val="0" :end-val="messageCount" :duration="3600" class="card-panel-num"/>
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import CountTo from 'vue-count-to'
import {getStatisticData} from "../../api/opq/statistic";

export default {
  components: {
    CountTo
  },
  data() {
    return {
      peoples: 0,
      messageCount: 0,
      message: 0
    }
  },
  methods: {
    handleSetLineChartData(type) {
      this.$emit('handleSetLineChartData', type)
    }
  },
  created() {
    getStatisticData().then(resp => {
      if (resp.code == 200) {
        //将活跃群组数赋值
        this.peoples = resp.data.peoples;
        //将总消息赋值
        this.messageCount = resp.data.messageCount;
        //将今日消息数赋值
        this.message = resp.data.message;

        this.$emit("getData", resp.data);
      }
    });
  }
}
</script>

<style lang="scss" scoped>
.panel-group {
  //margin-top: 2px;

  .card-panel-col {
    margin-bottom: 30px;
  }

  .card-panel {
    height: 90px;
    cursor: pointer;
    font-size: 8px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);
    border-radius: 10px;

    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }

      .icon-peoples {
        background: #40c9c6;
      }

      .icon-message {
        background: #36a3f7;
      }

      .icon-people {
        background: #e4f158;
      }

      .icon-messageCount {
        background: #34bfa3
      }
    }

    .icon-peoples {
      color: #40c9c6;
    }

    .icon-message {
      color: #36a3f7;
    }

    .icon-people {
      color: #e4e810;
    }

    .icon-messageCount {
      color: #34bfa3
    }

    .card-panel-icon-wrapper {
      float: left;
      margin: 8px 0 0 8px;
      padding: 10px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }

    .card-panel-icon {
      float: left;
      font-size: 48px;
    }

    .card-panel-description {
      //float: right;
      font-weight: bold;
      margin: 18px 18px 18px 140px;

      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }

      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}

@media (max-width: 550px) {
  .card-panel-description {
    display: none;
  }

  .card-panel-icon-wrapper {
    float: none !important;
    width: 100%;
    height: 100%;
    margin: 0 !important;

    .svg-icon {
      display: block;
      margin: 14px auto !important;
      float: none !important;
    }
  }
}
</style>
