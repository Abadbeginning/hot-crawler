var getTypesUrl = domain + "/api/v1/types";
var infoTypes;
$.ajax({
    url: getTypesUrl,
    type: "get",
    async: false,
    dataType: "json",
    success: function(res){
        //console.log(JSON.stringify(res));
        infoTypes = res.data;
        for (var i = 0; i < infoTypes.length; i++)
        {
            var menuItem = "<div class='menu-item pointer' id='"+infoTypes[i].id+"'>" + infoTypes[i].name + "</div>";
            $("#menu-bar").append(menuItem);
            var contentBlock = "<div id='content-block'></div>";
            $("#content").append(contentBlock)
        }
        // initial get infos
        getInfos(infoTypes[0].id);
    },
    error: function(res){
        alert(JSON.stringify(res))
    }
});

$(".menu-item").click(function () {
    var typeId = $(this).attr("id");
    getInfos(typeId);
    //console.log(infos);
});

function getInfos(typeId)
{
    var infos;
    var getInfoUrl = domain + "/api/v1/types/" + typeId + "/infos";
    $.ajax({
        url: getInfoUrl,
        type: "get",
        async: true,
        dataType: "json",
        success: function(res){
            infos = res.data;
            putInfos("#content-block", infos);
        },
        error: function(res){
            alert(JSON.stringify(res))
        }
    });
    return infos;
}

function putInfos(elementId, infos) {
    $(elementId).empty();
    for (var j = 0; j < infos.length; j++)
    {
        var infoItem = "<div class='info-item'><a href='"+infos[j].url+"'>" + (j+1) + ". " + infos[j].title  + "</a></div>";
        $(elementId).append(infoItem);
    }
}