// function tableChange(name){
//     const _this = this;
//     switch (name) {
//         default :_this.brand.tablename = "tb_brand";_this.selectALL();break;
//         case 'tb_brand':_this.brand.tablename = "tb_brand";_this.selectALL();break;
//         case 'tb_brandsec':_this.brand.tablename = "tb_brandsec";_this.selectALL();break;
//     }
// }
//
// function selectALL() {
//     //无法在axios使用this，所以在axios外定义了一个
//     var _this = this;
//     num = 1;
//     // console.log("aabb"+_this.brand.tablename);
//     //页面加载完后发送异步请求，获取数据
//     axios({
//         method:"post",
//         url:"http://localhost:8080/brand-case/selectByPageAndCondition?currentPage="+this.currentPage+"&pageSize="+this.pageSize,
//         data:_this.brand
//     }).then(resp =>{
//         //设置表格数据
//         _this.tableData = resp.data.rows; // {rows:[],totalCount:100}
//         //设置总记录数
//         _this.totalCount = resp.data.totalCount;
//     })
// }
//
// // selectALLSec() {
// //     //无法在axios使用this，所以在axios外定义了一个
// //     var _this = this;
// //     num = 2;
// //     //页面加载完后发送异步请求，获取数据
// //     axios({
// //         method:"get",
// //         url:"http://localhost:8080/brand-case/brand/selectAllSec",
// //         data:_this.brandSec
// //     }).then(resp =>{
// //         //设置表格数据
// //         _this.tableData = resp.data.rowsSec; // {rows:[],totalCount:100}
// //         //设置总记录数
// //         _this.totalCount = resp.data.totalCountSec;
// //     })
// // },
//
// function tableRowClassName({row, rowIndex}) {
//     if (rowIndex === 1) {
//         return 'warning-row';
//     } else if (rowIndex === 3) {
//         return 'success-row';
//     }
//     return '';
// }
//
// // 复选框选中后执行的方法
// function handleSelectionChange(val) {
//     this.multipleSelection = val;
// }
//
// // 查询方法
// function onSubmit() {
//     this.selectALL();
// }
//
// // 添加数据
// function addBrand(){
//     //console.log(this.brand);
//     //发送ajax请求，添加数据
//     var _this = this;
//     axios({
//         method: "post",
//         url:"http://localhost:8080/brand-case/addServlet",
//         data: _this.brandSec
//     }).then(function (response) {
//         if (response.data == "success"){
//             _this.selectALL();
//             //关闭窗口
//             _this.dialogVisible = false;
//
//             _this.$message({
//                 message:'添加成功!',
//                 type:'success'
//             });
//         }else {
//             _this.$message.error('添加失败');
//         }
//     })
// }
//
// function updateById(index, row) {
//     var _this = this;
//     _this.brand.id = row.id;
//     _this.addVisible = true;
// }
// //修改数据
// function updatebrand(){
//     var _this = this;
//     //发送ajax异步请求，添加数据
//     axios({
//         method: "post",
//         url: "http://localhost:8080/brand-case/updateServlet",
//         data: _this.brandThd
//     }).then(function (resp) {
//         if (resp.data == "success") {
//             //关闭窗口
//             _this.addVisible = false;
//             //查询一次
//             _this.selectALL();
//             _this.$message({
//                 message: '修改成功',
//                 type: 'success'
//             });
//         } else {
//             _this.$message.error('修改数据失败');
//         }
//     })
// }
//
// //删除数据
// function deleteById(index, row) {
//     var _this = this;
//     this.$confirm('此操作将删除该数据, 是否继续?', '提示', {
//         confirmButtonText: '确定',
//         cancelButtonText: '取消',
//         type: 'warning'
//     }).then(() => {
//         // 发送ajax请求，添加数据
//         axios({
//             method: "post",
//             url: "http://localhost:8080/brand-case/deleteServlet",
//             data: row.id
//         }).then(function (resp) {
//             if (resp.data == "success") {
//                 // 重新查询数据
//                 _this.selectALL();
//                 // 弹出消息提示
//                 _this.$message({
//                     message: '删除成功',
//                     type: 'success'
//                 });
//             }
//         })
//     }).catch(() => {
//         //用户点击取消按钮
//         this.$message({
//             type: 'info',
//             message: '已取消删除'
//         });
//     });
// }
//
//
// //批量删除的方法
// function deleteByIds(){
//     //1.创建id数组
//     for (let i = 0; i < this.multipleSelection.length; i++) {
//         let selectionElement = this.multipleSelection[i];
//         this.selectedIds[i] = selectionElement.id;
//     }
//     var _this = this;
//
//     _this.$confirm('该操作不可逆，是否确定要删除已选择的数据?', '警告', {
//         confirmButtonText: '确定',
//         cancelButtonText: '取消',
//         type: 'warning'
//     }).then(() => {
//         //确定
//
//         //发送ajax请求
//         axios({
//             method: "post",
//             url: "http://localhost:8080/brand-case/deleteByIdsServlet",
//             data: _this.selectedIds
//         }).then(function (resp) {
//             if (resp.data == "success") {
//                 //关闭窗口
//                 _this.addVisible = false;
//                 //查询一次
//                 _this.selectALL();
//                 _this.$message({
//                     message: '删除成功',
//                     type: 'success'
//                 });
//             } else {
//                 _this.$message.error('删除失败');
//             }
//         });
//     }).catch(() => {
//         //取消
//         _this.$message({
//             type: 'info',
//             message: '已取消删除'
//         });
//     });
//
// }
//
// //分页
// function handleSizeChange(val) {
//     this.pageSize  = val;
//     this.selectALL();
// }
// function handleCurrentChange(val) {
//     this.currentPage  = val;
//     this.selectALL();
// }
//
// function handleSelect(key, keyPath) {
//     console.log(key, keyPath);
// }
//
//
//
// function handleOpen(key, keyPath) {
//     console.log(key, keyPath);
// }
// function handleClose(key, keyPath) {
//     console.log(key, keyPath);
// }
//
// function data() {
//     return {
//         cup:1,
//         toc:1,
//
//         activeIndex: '1',
//         activeIndex2:'2',
//         pageSize:5,
//
//         totalCount:100,
//
//         addVisible: false,
//         // 当前页码
//         currentPage: 1,
//
//         // 添加数据对话框是否展示的标记
//         dialogVisible: false,
//
//         rows:[],
//         rowsSec:[],
//
//         // tablename:"tb_brand",
//
//         // 品牌模型数据
//         brand: {
//             status: '',
//             brandName: '',
//             companyName: '',
//             id:"",
//             ordered:"",
//             ordered2:"",
//             description:""
//         },
//
//         // 品牌模型数据
//         brandSec: {
//             status: '',
//             brandName: '',
//             companyName: '',
//             id:"",
//             ordered:"",
//             ordered2:"",
//             description:""
//         },
//
//         // 品牌模型数据
//         brandThd: {
//             status: '',
//             brandName: '',
//             companyName: '',
//             id:"",
//             ordered:"",
//             ordered2:"",
//             description:""
//         },
//
//         //批量删除中被选中的id数组
//         selectedIds:[],
//         // 复选框选中数据集合
//         multipleSelection: [],
//         // 表格数据
//         tableData: [{
//             brandName: '华为',
//             companyName: '华为科技有限公司',
//             ordered: '100',
//             status: "1"
//         }, {
//             brandName: '华为',
//             companyName: '华为科技有限公司',
//             ordered: '100',
//             status: "1"
//         }, {
//             brandName: '华为',
//             companyName: '华为科技有限公司',
//             ordered: '100',
//             status: "1"
//         }, {
//             brandName: '华为',
//             companyName: '华为科技有限公司',
//             ordered: '100',
//             status: "1"
//         }]
//     }
// }
