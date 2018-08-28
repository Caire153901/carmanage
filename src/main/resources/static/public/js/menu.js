/**
 * 获取当前用户所属公司的树形表格
 */
function renderOrgTree($jq,type,selectRow){
    $jq.data("flag","1");
    var option = {
        idField: 'id',
        fit: true,
        treeField: 'orgname',
        fitColumns: true,
        animate: true,
        panelWidth:300,
        columns: [[
            {field: 'id', width: 180, hidden: 'true'},
            {field: 'orgname', title: '组织名', width: '100%', align: 'left'}
        ]],
        onLoadSuccess: function (node, data) {
            if (data.length === 1 && data[0].id === 1) {
                if (type === 'treegrid') {
                    $jq.treegrid("expand",1);
                }
            }
        },
        onSelect: function (row) {
            if (selectRow)
                selectRow(row);
        },
        loader: function (params, success, error) {
            if ("1" === $jq.data("flag")) {
                $jq.data("flag", "2");
                $.ajax({
                    url: getRootPath__() + '/sys_manage/org_manage/getOrg',
                    dataType: 'json',
                    success: function (re) {
                        re[0].state = "closed";
                        success(re);
                    }
                });
            } else {
                var pid = params.id;
                $.ajax({
                    url: getRootPath__() + '/sys_manage/org_manage/getOrgList',
                    data: {
                        pid: pid
                    },
                    dataType: 'json',
                    success: function (re) {
                        if (re.length === 0) {
                            error();
                        }else {
                            $.each(re, function (i, v) {
                                if(v.leaf === "0"){
                                    v.state = 'closed';
                                }
                            });
                            success(re);
                        }
                    }
                });
            }
        }
    };
    if(type==="treegrid"){
        $jq.treegrid(option);
    }else if(type==="combotreegrid"){
        $jq.combotreegrid(option);
    }
}