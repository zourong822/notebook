<template>
  <div>
    <v-layout class="px-3 pb-2">
      <v-flex xs2>
        <v-btn small color="info">新增品牌</v-btn>
      </v-flex>
      <v-spacer/>
      <v-flex xs4>
        <v-text-field label="输入关键字搜索" append-icon="search" hide-details v-model="keyWord"/>
      </v-flex>
    </v-layout>
    <v-data-table
      :headers="headers"
      :items="brands"
      :pagination.sync="pagination"
      :total-items="totalBrands"
      :loading="loading"
      class="elevation-1"
      >
        <template slot="items" slot-scope="props">
                <td class="text-xs-center">{{ props.item.id }}</td>
                <td class="text-xs-center">{{ props.item.name }}</td>
                <td class="text-xs-center"> 
                    <img v-if="props.item.image" :src="props.item.image" width="130" height="40">
                    <span v-else>无</span>
                </td>
                <td class="text-xs-center">{{ props.item.letter }}</td>
                <td class="text-xs-center">
                  <v-btn flat icon color="info">
                      <v-icon>edit</v-icon>
                  </v-btn>
                  <v-btn flat icon color="error">
                      <v-icon>delete</v-icon>
                  </v-btn>
                </td>
        </template>
    </v-data-table>
  </div>
</template>

<script>
  export default {
    name:"MyBrands",
    data () {
      return {
        totalBrands: 15,
        brands: [],
        loading: true,
        pagination:{},
        headers:[
          { text: '品牌id',align: 'center',sortable: true,value: 'id',},
          { text: '名称',align: 'center', value: 'name' ,sortable:false},
          { text: 'LOGO',align: 'center', value: 'image',sortable:false },
          { text: '首字母',align: 'center', value: 'letter',sortable:true },
          { text: '操作',align: 'center', value: 'id',sortable:false },
        ],
        keyWord:""
      }
    },
    watch:{
        keyWord(){
          this.pagination.page=1;
        },
        pagination: {
            handler () {
              this.getDataFromServer();
            },
            deep: true,
        }
    },
    created(){
        this.brands=this.getDataFromServer();
    },
    methods:{
        getDataFromServer(){
            this.loading=true;
            this.$http.get("/item/brand/page",{params:{
              keyWord:this.keyWord,//搜索关键字
              desc:this.pagination.descending,//是否降序
              page:this.pagination.page,//第几页
              PageSize:this.pagination.rowsPerPage,//当前页大小
              sortBy:this.pagination.sortBy//排序字段
              }})
            .then((response)=>{
                this.brands=response.data.items;
                this.totalBrands=response.data.total;
                this.loading=false;
              })
            .catch(()=>{console.log("请求发送但发生了异常")});
            
        }
    }
  }
</script>