
/* 启动时加载 */
$(function(){
    $("#user_tab").datagrid({
        checkOnSelect: true,
        pagination:true,
        pageSize:20,//默认传参 rows
        pageNumber:1,//默认传参 page
        url: getRootPath__()+'/user/list',
        fitColumns:true,
        singleSelect:true,
        fit:true,
        method:'get',
        pageList : [10,20,30],
        sortName : 'gmtModified',//默认传参 sort
        sortOrder : 'desc',//默认传参 order吧
        rownumbers:true,
        onSelect:function(index, row){
           // selectUser(row);
        },
        onLoadSuccess:function(data){
            buttonDisble();
        },
        onLoadError:function () {
          alert(333)
        },
        columns: [[
            { field: 'id', title: '用户ID', hidden: 'true'},
            { field: 'roleId', title: '角色ID', hidden: 'true'},
            { field: 'account', title: '用户名',sortable:true, width:100, align: 'left', halign: 'center'},
            { field: 'roleName', title: '角色',sortable:true, width:50, halign: 'center'},
            { field: 'userName', title: '真实姓名',  align: 'left', halign: 'center',sortable:true,width:100},
            { field: 'useStatus', title: '状态',  align: 'center', halign: 'center',sortable:true,width:60,
                formatter: function(value,row,index){
                    if (value=='0'){
                        return "<span class='iconfont icon-chenggong' style='color:#1AE61A'></span>";
                    }
                    if (value=='2'){
                        return "<span class='iconfont icon-iconset0187' style='color:red'></span>";
                    }
                    if(value =='1'){
                        return "<span class='iconfont icon-dengpao' style='color:yellow'></span>";
                    }
                }
            }
        ]]
    });

    //选中后按钮状态
    function selectUser(row){
        var state=row.uState;
        $('#user_edit').linkbutton('enable');//修改按钮可用
        $('#user_remove').linkbutton('enable');//删除按钮可用
        if(state==0){
            $('#user_start').linkbutton('enable');//启用按钮可用
            $('#user_dis').linkbutton('disable');//禁用按钮禁用
        }else if(state==1){
            $('#user_dis').linkbutton('enable');//禁用按钮可用
            $('#user_start').linkbutton('disable');//启用按钮禁用
        }
    }
    //按钮禁用初始化
    function buttonDisble(){
        $('#user_edit').linkbutton('disable');//修改按钮
        $('#user_remove').linkbutton('disable');//删除按钮
        $('#user_start').linkbutton('disable');//启用按钮
        $('#user_dis').linkbutton('disable');//禁用按钮
    }

    /** 查询数据条件 */
    function checkInputQuery(){
        var realName = $('#realName').val(); //用户真实姓名
        $('#user_tab').datagrid('options').url=getRootPath__()+'/sys_manage/user_manage/getUserLists';
        $('#user_tab').datagrid('reload',{
            realname:realName,
        });
    }
    function reset(){
        $('#realName').val("");
        $('#user_tab').datagrid('load',{});
    }
});
