// var aindex = 1;
// var tindex = 1;
// var sindex = 1;
var rindex = 1;
var pindex = 1;
var edit_activity = false;
var edit_task = false;
var story_states = ['ready','todo','doing','done'];
var mapId;
var userId = 1;

$(function(){
    init();

    $('.add_backlog').on("click",function () {
        var tcard = $(this).parent().parent();
        var position = 1;
        if($('.activity_card').length>0)
            position = $('.activity_card:last').attr('data-position')+1;

        $.ajax({
            type: "post",
            url: "/createActivity",
            data:JSON.stringify({"mapId":mapId,"name":'text',"creatorId":userId,"position":position,"roles":[]}),
            contentType: "application/json; charset=utf-8",
            async: false,
            success: function (act) {
                var aid = act['data']['id'];

                $.ajax({
                    type: "post",
                    url: "/createTask",
                    data:JSON.stringify({"mapId":mapId,"name":'text',"creatorId":userId,"position":1,"roles":[],"parent":aid}),
                    contentType: "application/json; charset=utf-8",
                    async: false,
                    success: function (task) {
                        var tid = task['data']['id'];
                        $(tcard).before(createBacklogDiv(act['data'],task['data']));

                        $('.release_div').each(function () {
                            var s = '<div class="story_panel" data-aid="'+aid+'" style="margin-left: -5px;">\n' +
                                '<div class="story_list" data-tid="'+tid+'"><div class="story_plus_card">\n' +
                                '                        <div style="margin-top: 2px;text-align: center;">\n' +
                                '                            <img class="add_story" src="../icons/add_large.png" /></div>\n' +
                                '                    </div>\n' +
                                '                </div></div>';
                            $(this).find('.backlog_div:last').html(s);
                            var rh = $(this).height() - 20;
                            s = '<div class="backlog_div">\n' +
                                '                <div class="story_panel" style="height: '+rh+'px;" style="margin-left: -5px;">\n' +
                                '                    <div class="story_plus_card" style="margin-left: 5px;">\n' +
                                '                    </div>\n' +
                                '                </div>\n' +
                                '            </div>';
                            $(this).append(s);
                        });

                    }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                        alert(XMLHttpRequest.status);
                        alert(XMLHttpRequest.readyState);
                        alert(textStatus);
                    }
                });

            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
                alert(textStatus);
            }
        });

    });

    personCardBind();
    addPerson();
    removePersonCard();
    modifyPersonCard();
    activityCardBind();
    addActivityCard();
    removeActivityCard();
    modifyActicityCard();
    removeTaskCard();
    modifyTaskCard();
    addTaskCard();
    taskCardBind();
    storyCardBind();
    addFirstStoryCard();
    removeStoryCard();
    addNextStoryCard();
    modifyStoryCard();

    addReleaseDiv();
    removeReleaseDiv();
});

function personCardBind() {
    $('body').on("mouseover",'.person_card',function () {
        $(this).find('.person_card_operation').show();
    });
    $('body').on("mouseout",'.person_card',function () {
        $(this).find('.person_card_operation').hide();
    });
}

function addPerson() {
    $('body').on("click",'.add_person_card',function () {
        var aid = $(this).parent().parent().find('.activity_card').attr('data-aid');
        $('#addPersonModal').attr('data-aid',aid);
        $('#addPersonModal').modal('show');
    });

    $('#modal_addRole_button').click(function () {
        var aid = $('#addPersonModal').attr('data-aid');
        var personName = $('#personNameInput').val();
        var imgpath = $('input[name="roleOption"]:checked').val();

        $.ajax({
            type: "post",
            url: "/createRole",
            data:{"mapId":mapId,"name":personName,"avatar":imgpath},
            async: false,
            success: function (data) {

                $('.activity_card[data-aid='+aid+']').parent().find('.person_panel').append('<div class="person_card" data-name="'+personName
                    +'" title="'+personName+'" data-pid="'+pindex+'">\n' +
                    '                            <img src="../portraits/'+imgpath+'.png" width="30" height="30" class="person_img"/>\n' +
                    '                            <div class="person_card_operation" style="display: none;">\n' +
                    '                                <img src="../icons/trash.png" class="remove_person_icon removePerson"/>\n' +
                    '                            </div>\n' +
                    '                        </div>');
                pindex+=1;
                $('#addPersonModal').modal('hide');

            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
                alert(textStatus);
            }
        });

    });
}

function removePersonCard() {
    $('body').on("click",'.removePerson',function () {
        $(this).parent().parent().remove();

    });
}

function modifyPersonCard() {
    $('body').on("dblclick",'.person_card',function () {
        var imgpath = $(this).find('.person_img').attr('src');
        var opval = parseInt(imgpath.split('/')[1].substring(0));
        var pname = $(this).attr('data-name');
        var pid = $(this).attr('data-pid');

        $('#modifyPersonModal').attr('data-pid',pid);
        $('#personNameInput_m').val(pname);
        $('input[name="roleOption2"]').eq(opval-1).attr("checked",'checked');
        $('#modifyPersonModal').modal('show');
    });

    $('#modal_modifyRole_button').click(function () {
        var pid = $('#modifyPersonModal').attr('data-pid');
        var personName = $('#personNameInput_m').val();
        var imgpath = $('input[name="roleOption2"]:checked').val();

        var pcard = $('.person_card[data-pid='+pid+']');
        $(pcard).attr('data-name',personName);
        $(pcard).attr('title', personName);
        $(pcard).find('.person_img').attr('src','../portraits/'+imgpath+'.png');
        $('#modifyPersonModal').modal('hide');

        // $.ajax({
        //     type: "post",
        //     url: "/createRole",
        //     data:{"mapId":mapId,"name":personName,"avatar":imgpath},
        //     async: false,
        //     success: function (data) {
        //
        //         var pcard = $('.person_card[data-pid='+pid+']');
        //         $(pcard).attr('data-name',personName);
        //         $(pcard).attr('title', personName);
        //         $(pcard).find('.person_img').attr('src','../portraits/'+imgpath+'.png');
        //         $('#modifyPersonModal').modal('hide');
        //
        //     }, error: function (XMLHttpRequest, textStatus, errorThrown) {
        //         alert(XMLHttpRequest.status);
        //         alert(XMLHttpRequest.readyState);
        //         alert(textStatus);
        //     }
        // });
    });
}


function createBacklogDiv(acardInfo,tcardInfo) {
    var bdiv = '<div class="backlog_div">\n' +
        '                <div>\n' +
        '<div style="display: inline-block;" class="person_panel">\n' +
        '                    </div>' +
        '                    <img class="add_person_card" src="../icons/add_large.png" />\n' +
        '                </div>\n' +
        '                <div class="activity_card" data-aid="'+acardInfo['id']+'" data-position="'+acardInfo['position']+'" data-creator="'+acardInfo['creator']+'" data-creatAt="'+acardInfo['creatAt']+'">\n' +
        '                    <div class="activity_textDiv">text</div>\n' +
        '                    <div class="activity_estimation"></div>\n' +
        '                    <div class="activity_card_operation" style="display: none;">\n' +
        '                    <img src="../icons/left-arrow.png" class="add_left_icon addActivityLeft"/>\n' +
        '                    <img src="../icons/trash.png" class="remove_icon removeActivity"/>\n' +
        '                    <img src="../icons/right-arrow.png" class="add_right_icon addActivityRight"/>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '                <div style="margin-left: -5px;">\n' +
        '                    <div class="task_card" data-tid="'+tcardInfo['id']+'" data-position="'+tcardInfo['position']+'" data-creator="'+tcardInfo['creator']+'" data-creatAt="'+tcardInfo['creatAt']+'">\n' +
        '                        <div class="activity_textDiv">text</div>\n' +
        '                        <div class="task_estimation"></div>\n' +
        '                        <div class="activity_card_operation" style="display: none;">\n' +
        '                            <img src="../icons/left-arrow.png" class="add_left_icon addTaskLeft"/>\n' +
        '                            <img src="../icons/trash.png" class="remove_icon removeTask" style="display: none;"/>\n' +
        '                            <img src="../icons/right-arrow.png" class="add_right_icon addTaskRight"/>\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </div>';

    return bdiv;
}
             
function addActivityCard() {
    $('body').on("click",'.addActivityLeft',function () {
        var pcard = $(this).parent().parent().parent();
        var aid_old = $(this).parent().parent().attr('data-aid');
        var acard = $(this).parent().parent();
        var bnode = this.parentNode.parentNode.parentNode;

        var position_R = $(acard).attr('data-position');
        var position_L = position_R;
        if(bnode.previousSibling!=null)
            position_L = bnode.previousSibling.childNodes[1].getAttribute('data-position');

        $.ajax({
            type: "post",
            url: "/createActivity",
            data:JSON.stringify({"mapId":mapId,"name":'text',"creatorId":userId,"position":(position_L+position_R)/2,"roles":[]}),
            contentType: "application/json; charset=utf-8",
            async: false,
            success: function (act) {
                var aid = act['data']['id'];

                $.ajax({
                    type: "post",
                    url: "/createTask",
                    data:JSON.stringify({"mapId":mapId,"name":'text',"creatorId":userId,"position":1,"roles":[],"parent":aid}),
                    contentType: "application/json; charset=utf-8",
                    async: false,
                    success: function (task) {
                        var tid = task['data']['id'];
                        $(pcard).before(createBacklogDiv(act['data'],task['data']));

                        $('.release_div').each(function () {
                            var op = '.story_panel[data-aid='+aid_old+']';
                            var s = '<div class="backlog_div">\n' +
                                '<div class="story_panel" data-aid="'+aid+'" style="margin-left: -5px;">\n' +
                                '<div class="story_list" data-tid="'+tid+'"><div class="story_plus_card">\n' +
                                '                        <div style="margin-top: 2px;text-align: center;">\n' +
                                '                            <img class="add_story" src="../icons/add_large.png" /></div>\n' +
                                '                    </div>\n' +
                                '                </div>'+
                                '                </div></div>';
                            $(this).find(op).parent().before(s);
                        });

                    }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                        alert(XMLHttpRequest.status);
                        alert(XMLHttpRequest.readyState);
                        alert(textStatus);
                    }
                });

            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
                alert(textStatus);
            }
        });
    });

    $('body').on("click",'.addActivityRight',function () {
        var pcard = $(this).parent().parent().parent();
        var aid_old = $(this).parent().parent().attr('data-aid');
        var acard = $(this).parent().parent();
        var bnode = this.parentNode.parentNode.parentNode;
        var position_L = $(acard).attr('data-position');
        var position_R = position_L;
        if(bnode.nextSibling!=null)
            position_R = bnode.nextSibling.childNodes[1].getAttribute('data-position');

        $.ajax({
            type: "post",
            url: "/createActivity",
            data:JSON.stringify({"mapId":mapId,"name":'text',"creatorId":userId,"position":(position_L+position_R)/2,"roles":[]}),
            contentType: "application/json; charset=utf-8",
            async: false,
            success: function (act) {
                var aid = act['data']['id'];

                $.ajax({
                    type: "post",
                    url: "/createTask",
                    data:JSON.stringify({"mapId":mapId,"name":'text',"creatorId":userId,"position":1,"roles":[],"parent":aid}),
                    contentType: "application/json; charset=utf-8",
                    async: false,
                    success: function (task) {
                        var tid = task['data']['id'];
                        $(pcard).after(createBacklogDiv(act['data'],task['data']));

                        $('.release_div').each(function () {
                            var op = '.story_panel[data-aid='+aid_old+']';
                            var s = '<div class="backlog_div">\n' +
                                '<div class="story_panel" data-aid="'+aid+'" style="margin-left: -5px;">\n' +
                                '<div class="story_list" data-tid="'+tid+'"><div class="story_plus_card">\n' +
                                '                        <div style="margin-top: 2px;text-align: center;">\n' +
                                '                            <img class="add_story" src="../icons/add_large.png" /></div>\n' +
                                '                    </div>\n' +
                                '                </div>'+
                                '                </div></div>';
                            $(this).find(op).parent().after(s);
                        });

                    }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                        alert(XMLHttpRequest.status);
                        alert(XMLHttpRequest.readyState);
                        alert(textStatus);
                    }
                });

            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
                alert(textStatus);
            }
        });
    });

}

function activityCardBind() {
    $('body').on("mouseover",'.activity_card',function () {
        $(this).find('.activity_card_operation').show();
    });
    $('body').on("mouseout",'.activity_card',function () {
        $(this).find('.activity_card_operation').hide();
    });
}

function removeActivityCard() {
    $('body').on("click",'.removeActivity',function () {
        var aid = $(this).parent().parent().attr('data-aid');

        // $.ajax({
        //     type: "post",
        //     url: "/createActivity",
        //     data:{"mapId":mapId,"name":'text',"creatorId":userId,"position":(position_L+position_R)/2,"roles":[]},
        //     async: false,
        //     success: function (act) {
        //
        //     }, error: function (XMLHttpRequest, textStatus, errorThrown) {
        //         alert(XMLHttpRequest.status);
        //         alert(XMLHttpRequest.readyState);
        //         alert(textStatus);
        //     }
        // });
        $(this).parent().parent().parent().remove();
        var op = '.story_panel[data-aid='+aid+']';
        $(op).parent().remove();
    });
}

function modifyActicityCard() {
    $('body').on("dblclick",'.activity_card',function () {
        var aid = $(this).attr('data-aid');
        var content = $(this).find('.activity_textDiv').html();
        var acard = $('.activity_card[data-aid='+aid+']');
        var creator = $(acard).attr('data-creator');
        var creatAt = $(acard).attr('data-creatAt').split(' ')[0];
        $('#activityModal').find('.modal_content').html(content);
        $('#activityModal').attr('data-aid',aid);
        $('#activityCard_creator').text(creator);
        $('#activityCard_creatAt').text(creatAt);

        var personList = $(this).parent().find('.person_panel').find('.person_card');
        var s = '';
        var i;
        for(i = 0;i<personList.length;i++){
            var pname = personList.eq(i).attr('data-name');
            var imgpath = personList.eq(i).find('.person_img').attr('src');
            s+='<div class="modal_person_div">\n' +
                '                            <img src="'+imgpath+'" width="20" height="20"/>\n' +
                '                            <span style="margin-left: 5px;">'+pname+'</span>\n' +
                '                        </div>';
        }
        $('#activityModal').find('.modal_person_panel').html(s);
        $('#activityModal').modal('show');
    });

    $('#activityModal').on("dblclick",'.modal_content',function () {
        if(edit_activity){
            var aid = $('#activityModal').attr('data-aid');
            var content = $('#activityModal').find('.modal_content textarea').val();
            $('.activity_card[data-aid='+aid+']').find('.activity_textDiv').html(content);
            $('#activityModal').find('.modal_content').html(content);
        }else{
            var content = $(this).text();
            var s = '<textarea type="text" class="form-control" rows="11">'+content+'</textarea>\n' +
                '                        <div class="saveActivity txtArea_saveIcon"><i class="fa fa-check"></i></div>\n' +
                '                        <div class="cancelModifyAct txtArea_cancelModifyAct"><i class="fa fa-close"></i></div>';
            $(this).html(s);
        }
        edit_activity = !edit_activity;
    });

    $('#activityModal').on("click",'.saveActivity',function () {
        var aid = $('#activityModal').attr('data-aid');
        var content = $('#activityModal').find('.modal_content textarea').val();
        var position = $('.activity_card[data-aid='+aid+']').attr('data-position');

        $('.activity_card[data-aid='+aid+']').find('.activity_textDiv').html(content);
        $('#activityModal').find('.modal_content').html(content);
        // $.ajax({
        //     type: "post",
        //     url: "/modifyActivity",
        //     data:{"id":aid,"mapId":mapId,"name":content,"creatorId":userId,"position":position},
        //     async: false,
        //     success: function (act) {
        //         $('.activity_card[data-aid='+aid+']').find('.activity_textDiv').html(content);
        //         $('#activityModal').find('.modal_content').html(content);
        //
        //     }, error: function (XMLHttpRequest, textStatus, errorThrown) {
        //         alert(XMLHttpRequest.status);
        //         alert(XMLHttpRequest.readyState);
        //         alert(textStatus);
        //     }
        // });

    });

    $('#activityModal').on("click",'.cancelModifyAct',function () {
        var aid = $('#activityModal').attr('data-aid');
        var content = $('.activity_card[data-aid='+aid+']').find('.activity_textDiv').html();
        $('#activityModal').find('.modal_content').html(content);
    });
}



function taskCardBind() {
    $('body').on("mouseover",'.task_card',function () {
        $(this).find('.activity_card_operation').show();
    });
    $('body').on("mouseout",'.task_card',function () {
        $(this).find('.activity_card_operation').hide();
    });
}

function removeTaskCard() {
    $('body').on("click",'.removeTask',function () {
        var tid = $(this).parent().parent().attr('data-tid');
        var bdiv = $(this).parent().parent().parent();
        $(bdiv).find('.task_card').find('.removeTask').show();
        var num = $(bdiv).find('.task_card').length;

        if(num == 2){
            $(bdiv).find('.task_card').eq(0).find('.removeTask').hide();
        }

        $(this).parent().parent().remove();
        var op = '.story_list[data-tid='+tid+']';
        $(op).remove();
    });
}

function createTaskCard(tid) {
    var tcard = '<div class="task_card" data-tid="'+tid+'">\n' +
        '                        <div class="activity_textDiv">text</div>\n' +
        '                        <div class="task_estimation"></div>\n' +
        '                        <div class="activity_card_operation" style="display: none;">\n' +
        '                            <img src="../icons/left-arrow.png" class="add_left_icon addTaskLeft"/>\n' +
        '                            <img src="../icons/trash.png" class="remove_icon removeTask"/>\n' +
        '                            <img src="../icons/right-arrow.png" class="add_right_icon addTaskRight"/>\n' +
        '                        </div>\n' +
        '                    </div>';

    return tcard;
}

function addTaskCard() {
    $('body').on("click",'.addTaskLeft',function () {
        var aid = $(this).parent().parent().parent().parent().find('.activity_card').attr('data-aid');
        var tcard = $(this).parent().parent();
        var tnode = this.parentNode.parentNode;
        var position_R = $(tcard).attr('data-position')
        var position_L = position_R;
        if(tnode.previousSibling!=null)
            position_L = tnode.previousSibling.getAttribute('data-position');

        $.ajax({
            type: "post",
            url: "/createTask",
            data:JSON.stringify({"mapId":mapId,"name":'text',"creatorId":userId,"position":(position_L+position_R)/2,"roles":[],"parent":aid}),
            contentType: "application/json; charset=utf-8",
            async: false,
            success: function (task) {
                var tid = task['data']['id'];
                $(tcard).before(createTaskCard(tid));
                $(tcard).parent().find('.task_card').find('.removeTask').show();
                addStoryPlusCard(aid,tid);

            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
                alert(textStatus);
            }
        });

    });

    $('body').on("click",'.addTaskRight',function () {
        var aid = $(this).parent().parent().parent().parent().find('.activity_card').attr('data-aid');
        var tcard = $(this).parent().parent();
        var tnode = this.parentNode.parentNode;
        var position_L = $(tcard).attr('data-position');
        var position_R = position_L;
        if(tnode.nextSibling!=null)
            position_R = tnode.nextSibling.getAttribute('data-position');

        $.ajax({
            type: "post",
            url: "/createTask",
            data:JSON.stringify({"mapId":mapId,"name":'text',"creatorId":userId,"position":(position_L+position_R)/2,"roles":[],"parent":aid}),
            contentType: "application/json; charset=utf-8",
            async: false,
            success: function (task) {
                var tid = task['data']['id'];
                $(tcard).after(createTaskCard(tid));
                $(tcard).parent().find('.task_card').find('.removeTask').show();
                addStoryPlusCard(aid,tid);

            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
                alert(textStatus);
            }
        });
    });
}

function addStoryPlusCard(aid, tid) {
    var op = '.story_panel[data-aid='+aid+']';
    var sc = '<div class="story_list" data-tid="'+tid+'"><div class="story_plus_card">\n' +
        '                    <div style="margin-top: 2px;text-align: center;">\n' +
        '                        <img class="add_story" src="../icons/add_large.png" /></div>\n' +
        '                </div></div>';
    $('.release_div').find(op).append(sc);
}

function modifyTaskCard() {
    $('body').on("dblclick",'.task_card',function () {
        var tid = $(this).attr('data-tid');
        var content = $(this).find('.activity_textDiv').html();
        var tcard = $('.task_card[data-tid='+tid+']');
        var creator = $(tcard).attr('data-creator');
        var creatAt = $(tcard).attr('data-creatAt').split(' ')[0];
        $('#taskCard_creator').text(creator);
        $('#taskCard_creatAt').text(creatAt);
        $('#taskModal').find('.modal_content').html(content);
        $('#taskModal').attr('data-tid',tid);

        $('#taskModal').modal('show');
    });

    $('#taskModal').on("dblclick",'.modal_content',function () {
        if(edit_task){
            var tid = $('#taskModal').attr('data-tid');
            var content = $('#taskModal').find('.modal_content textarea').val();
            $('.task_card[data-tid='+tid+']').find('.activity_textDiv').html(content);
            $('#taskModal').find('.modal_content').html(content);

        }else{
            var content = $(this).text();
            console.log(content)
            var s = '<textarea type="text" class="form-control" rows="11">'+content+'</textarea>\n' +
                '                        <div class="saveTask txtArea_saveIcon"><i class="fa fa-check"></i></div>\n' +
                '                        <div class="cancelModifyTask txtArea_cancelModifyAct"><i class="fa fa-close"></i></div>';
            $(this).html(s);
        }
        edit_task = !edit_task;
    });

    $('#taskModal').on("click",'.saveTask',function () {
        var tid = $('#taskModal').attr('data-tid');
        var content = $('#taskModal').find('.modal_content textarea').val();


        // $.ajax({
        //     type: "post",
        //     url: "/modifyTask",
        //     data:{"mapId":mapId,"name":'text',"creatorId":userId,"position":(position_L+position_R)/2,"roles":[]},
        //     async: false,
        //     success: function (act) {
        //
        //     }, error: function (XMLHttpRequest, textStatus, errorThrown) {
        //         alert(XMLHttpRequest.status);
        //         alert(XMLHttpRequest.readyState);
        //         alert(textStatus);
        //     }
        // });
        $('.task_card[data-tid='+tid+']').find('.activity_textDiv').html(content);
        $('#taskModal').find('.modal_content').html(content);
    });

    $('#taskModal').on("click",'.cancelModifyTask',function () {
        var tid = $('#taskModal').attr('data-tid');
        var content = $('.task_card[data-tid='+tid+']').find('.activity_textDiv').html();
        $('#taskModal').find('.modal_content').html(content);
    });
}


function storyCardBind() {
    $('body').on("mouseover",'.story_card',function () {
        $(this).find('.activity_card_operation').show();
    });
    $('body').on("mouseout",'.story_card',function () {
        $(this).find('.activity_card_operation').hide();
    });
}

function createStoryCard(story) {
    var s = '<div class="story_card" data-sid="'+story['id']+'" data-position="'+story['position']+'" data-creator="' +
                story['creator']+'" data-creatAt="'+story['createAt']+'">\n' +
        '                            <div class="activity_textDiv">text</div>\n' +
        '                            <div class="story_state todo">Todo</div>\n' +
        '                            <div class="story_estimation"></div>\n' +
        '                            <div class="activity_card_operation" style="display: none;">' +
        '                            <img src="../icons/up-arrow.png" class="add_up_icon addStoryUp"/>' +
        '                            <img src=" ../icons/trash.png" class="remove_icon removeStory"/>\n' +
        '                            <img src="../icons/down-arrow.png" class="add_right_icon addStoryDown"/>\n' +
        '                        </div>' +
        '                        </div>';

    return s;
}

function addFirstStoryCard() {
    $('body').on("click",'.add_story',function () {
        var pdiv = $(this).parent().parent().parent();
        var tid = $(pdiv).attr('data-tid');
        var rid = $(pdiv).parent().parent().attr('data-rid');
        $.ajax({
            type: "post",
            url: "/createStory",
            data:JSON.stringify({"mapId":mapId,"name":'text',"creatorId":userId,"position":1,"parent":tid,"status":1,"worktime":2,"release":rid,"roles":[]}),
            contentType: "application/json; charset=utf-8",
            async: false,
            success: function (data) {
                $(pdiv).html(createStoryCard(data));
            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
                alert(textStatus);
            }
        });
    });
}

function removeStoryCard() {
    $('body').on("click",'.removeStory',function () {
        var slist = $(this).parent().parent().parent()
        var num = $(slist).find('.story_card').length;
        // var tid = $(this).parent().parent().parent().attr('data-tid');
        $(this).parent().parent().remove();
        if(num==1){
            $(slist).html('<div class="story_plus_card">\n' +
                '                    <div style="margin-top: 2px;text-align: center;">\n' +
                '                        <img class="add_story" src="../icons/add_large.png" /></div>\n' +
                '                </div>');
        }
    });
}

function addNextStoryCard() {
    $('body').on("click",'.addStoryDown',function () {
        var tid = $(this).parent().parent().parent().attr('data-tid');
        var rid = $(this).parent().parent().parent().parent().parent().parent().attr('data-rid');
        var scard = $(this).parent().parent();
        var snode = this.parentNode.parentNode;
        var pUp = $(scard).attr('data-position');
        var pDown = pUp*3;
        if(snode.nextSibling!=null)
            pDown = snode.nextSibling.getAttribute('data-position');

        $.ajax({
            type: "post",
            url: "/createStory",
            data:JSON.stringify({"mapId":mapId,"name":'text',"creatorId":userId,"position":(pUp+pDown)/2,"parent":tid,"status":1,"worktime":2,"release":rid,"roles":[]}),
            contentType: "application/json; charset=utf-8",
            async: false,
            success: function (data) {
                $(scard).after(createStoryCard(data));
            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
                alert(textStatus);
            }
        });
    });

    $('body').on("click",'.addStoryUp',function () {
        var tid = $(this).parent().parent().parent().attr('data-tid');
        var rid = $(this).parent().parent().parent().parent().parent().parent().attr('data-rid');
        var scard = $(this).parent().parent();
        var snode = this.parentNode.parentNode;
        var pDown = $(scard).attr('data-position');
        var pUp = 0;
        if(snode.previousSibling!=null)
            pUp = snode.previousSibling.getAttribute('data-position');

        $.ajax({
            type: "post",
            url: "/createStory",
            data:JSON.stringify({"mapId":mapId,"name":'text',"creatorId":userId,"position":(pUp+pDown)/2,"parent":tid,"status":1,"worktime":2,"release":rid,"roles":[]}),
            contentType: "application/json; charset=utf-8",
            async: false,
            success: function (data) {
                $(scard).before(createStoryCard(data));
            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
                alert(textStatus);
            }
        });
    });
}

function modifyStoryCard() {
    $('body').on("dblclick",'.story_card',function () {
        var sid = $(this).attr('data-sid');
        var estimation = $(this).find('.story_estimation').html();
        var state = $(this).find('.story_state').html();
        var content = $(this).find('.activity_textDiv').html();
        var scard = $('.story_card[data-sid='+sid+']');
        var creator = $(scard).attr('data-creator');
        var creatAt = $(scard).attr('data-creatAt').split(' ')[0];
        $('#storyCard_creator').text(creator);
        $('#storyCard_creatAt').text(creatAt);

        $('#storyModal').attr('data-sid',sid);
        $('#story_estimation').val(estimation);
        $('#story_state_select').val(state);
        $('#story_content').html(content);

        $('#storyModal').modal('show');
    });

    $('#saveStory_button').click(function () {
        var sid = $('#storyModal').attr('data-sid');
        var estimation = $('#story_estimation').val();
        var state = $('#story_state_select').val();
        var content = $('#story_content').val();

        var scard = $('.story_card[data-sid='+sid+']');
        $(scard).find('.story_estimation').html(estimation);
        $(scard).find('.story_state').html(state);
        $(scard).find('.story_state').attr('class','story_state '+state.toLowerCase());
        $(scard).find('.activity_textDiv').html(content);

        var tid = $(scard).parent().attr('data-tid');
        var aid = $(scard).parent().parent().attr('data-aid');
        var t_estimation = 0;
        var a_estimation = 0;
        var sList = $('.story_list[data-tid='+tid+']').find('.story_card');
        for(var i = 0;i<sList.length;i++){
            t_estimation+=parseInt(sList.eq(i).find('.story_estimation').html());
        }
        sList = $('.story_panel[data-aid='+aid+']').find('.story_card');
        for(var i = 0;i<sList.length;i++){
            a_estimation+=parseInt(sList.eq(i).find('.story_estimation').html());
        }
        $('.task_card[data-tid='+tid+']').find('.task_estimation').html(t_estimation);
        $('.activity_card[data-aid='+aid+']').find('.activity_estimation').html(a_estimation);

        $('#storyModal').modal('hide');
    });
}


function addReleaseDiv() {
    $('body').on("click",'.add_release_icon',function () {
        $(this).parent().before('<div style="margin-top: 15px;" class="release_label" data-rid="'+rindex+'"><span>——Release '+rindex +
            '            </span><div style="display: inline-block;width: 20px;height: 20px;">\n' +
            '                <img src="../icons/trash.png" class="remove_release"/>\n' +
            '            </div>\n' +
            '        </div><div class="release_div" data-rid="'+rindex+'"></div>');

        var s = '';
        var aList = $('.activity_card');
        for(var i = 0;i<aList.length;i++){
            var aid = aList.eq(i).attr('data-aid');
            s+='<div class="backlog_div">\n' +
                '                <div class="story_panel"  data-aid="'+aid+'" style="margin-left: -5px;">';
            var tList = aList.eq(i).parent().find('.task_card');
            for(var j = 0;j<tList.length;j++){
                var tid = tList.eq(j).attr('data-tid');
                s+='<div class="story_list" data-tid="'+tid+'"><div class="story_plus_card">\n' +
                    '                    <div style="margin-top: 2px;text-align: center;">\n' +
                    '                        <img class="add_story" src="../icons/add_large.png" /></div>\n' +
                    '                </div></div>';
            }
            s+='</div></div>';
        }
        s+='<div class="backlog_div">\n' +
            '                <div class="story_panel"  style="margin-left: -5px;">\n' +
            '                    <div class="story_plus_card">\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '            </div>';

        $('.release_div[data-rid='+rindex+']').html(s);
        rindex+=1;
        $('.remove_release').show();
    });
}

function removeReleaseDiv() {
    $('body').on("click",'.remove_release',function () {
        var rid = $(this).parent().parent().attr('data-rid');
        $(this).parent().parent().remove();
        $('.release_div[data-rid='+rid+']').remove();

        var rList = $('.release_div');
        rindex = rList.length;
        for(var i = 1;i<=rindex;i++){
            var rid_tmp_old = rList.eq(i-1).attr('data-rid');
            rList.eq(i-1).attr('data-rid',i);
            $('.release_label[data-rid='+rid_tmp_old+']').find('span').text('——Release '+i);
        }
        if(rindex==1){
            $('.remove_release').hide();
        }
    });
}


function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var reg_rewrite = new RegExp("(^|/)" + name + "/([^/]*)(/|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    var q = window.location.pathname.substr(1).match(reg_rewrite);
    if(r != null){
        return unescape(r[2]);
    }else if(q != null){
        return unescape(q[2]);
    }else{
        return null;
    }
}

function init() {
    mapId = getQueryString('mapId');

    $.ajax({
        type: "get",
        url: "/getMapCards",
        data:{"mapId":parseInt(mapId)},
        async: false,
        success: function (data) {
            var dList = data['data'];
            var rslist = {};
            var bdiv = '';
            for(var i = 0;i<dList.length;i++){
                bdiv += '<div class="backlog_div">';
                var pList = dList[i]['roles'];
                var aid = dList[i]['id'];
                var aposition = dList[i]['position'];
                var aname = dList[i]['name'];
                var aestimation = 0;
                var acreator = dList[i]['creator'];
                var acreatAt = dList[i]['createAt'];
                var tList = dList[i]['tasks'];

                if(pList!=null){
                    var pdiv = '<div>';
                    for(var j = 0;j<pList.length;j++){
                        var role = pList[j];
                        pdiv+='<div style="display: inline-block;" class="person_panel">\n' +
                            '                        <div class="person_card" data-name="'+role['name']+'" title="'+role['name']+'" data-pid="'+pindex+'">\n' +
                        '                            <img src="../portraits/'+role['avatar']+'.png}" width="30" height="30" class="person_img"/>\n' +
                        '                            <div class="person_card_operation" style="display: none;">\n' +
                        '                                <img src="../icons/trash.png" class="remove_person_icon removePerson"/>\n' +
                        '                            </div>\n' +
                        '                        </div>\n' +
                        '                    </div>';
                        pindex++;
                    }
                    pdiv+='<img class="add_person_card" src="../icons/add_large.png" />\n' +
                        '                </div>';
                }
                bdiv+=pdiv;

                bdiv+='<div class="activity_card" data-aid="'+aid+'" data-position="'+aposition+'" data-creator="'+acreator+'" data-creatAt="'+acreatAt+'">\n' +
                    '                    <div class="activity_textDiv">'+aname+'</div>\n' +
                    '                    <div class="activity_estimation">'+aestimation+'</div>\n' +
                    '                    <div class="activity_card_operation" style="display: none;">\n' +
                    '                    <img src="../icons/left-arrow.png" class="add_left_icon addActivityLeft"/>\n' +
                    '                    <img src="../icons/trash.png" class="remove_icon removeActivity"/>\n' +
                    '                    <img src="../icons/right-arrow.png" class="add_right_icon addActivityRight"/>\n' +
                    '                    </div>\n' +
                    '                </div>';

                var tdiv ='<div style="margin-left: -5px;">';
                for(var k = 0;k<tList.length;k++){
                    var task = tList[k];
                    var tid = task['id'];
                    tdiv+='<div class="task_card" data-tid="'+tid+'" data-position="'+task['position']+'" data-creator="'+task['creator']+'" data-creatAt="'+task['createAt']+'">\n' +
                        '                        <div class="activity_textDiv">'+task['name']+'</div>\n' +
                        '                        <div class="task_estimation">'+0+'</div>\n' +
                        '                        <div class="activity_card_operation" style="display: none;">\n' +
                        '                            <img src="../icons/left-arrow.png" class="add_left_icon addTaskLeft"/>\n' +
                        '                            <img src="../icons/trash.png" class="remove_icon removeTask" style="display: none;"/>\n' +
                        '                            <img src="../icons/right-arrow.png" class="add_right_icon addTaskRight"/>\n' +
                        '                        </div>\n' +
                        '                    </div>';

                    var sList = task['storys'];
                    for(var m = 0;m<sList.length;m++){
                        var story = sList[m];
                        var rid = story['release'];
                        var sid = story['id'];
                        if(rslist[rid]==undefined){
                            rslist[rid] = {};
                            rslist[rid][aid] = {};
                            rslist[rid][aid][tid] = {};
                        }else{
                            if(rslist[rid][aid]==undefined){
                                rslist[rid][aid] = {};
                                rslist[rid][aid][tid] = {};
                            }else{
                                if(rslist[rid][aid][tid]==undefined){
                                    rslist[rid][aid][tid] = {};
                                }
                            }
                        }
                        rslist[rid][aid][tid][sid] = story;
                    }
                }
                tdiv+='</div>';
                bdiv+=tdiv;
                bdiv+='</div>';
            }
            $('.add_backlog').parent().parent().before(bdiv);


            var rdiv = '';
            for(var i in rslist){
                rdiv+='<div style="margin-top: 15px;" data-rid="'+i+'" class="release_label"><span>——Release '+i+'</span>\n' +
                    '            <div style="display: inline-block;width: 20px;height: 20px;">\n' +
                    '                <img src="../icons/trash.png" class="remove_release" style="display: none;"/>\n' +
                    '            </div>\n' +
                    '        </div>\n' +
                    '        <div class="release_div" data-rid="'+i+'">';
                for(var j in rslist[i]){
                    rdiv+='<div class="backlog_div"><div class="story_panel"  data-aid="'+j+'" style="margin-left: -5px;">';
                    for(var k in rslist[i][j]){
                        rdiv+='<div class="story_list" data-tid="'+k+'">';
                        for(var m in rslist[i][j][k]){
                            var story = rslist[i][j][k][m];
                            var state = story_states[story['status']];
                            rdiv+='<div class="story_card" data-sid="'+story['id']+'" data-position="'+story['position']+'" data-creator="' +
                                    story['creator']+'" data-creatAt="'+story['createAt']+'">\n' +
                                '                            <div class="activity_textDiv">'+story['name']+'</div>\n' +
                                '                            <div class="story_state '+state+'">'+state+'</div>\n' +
                                '                            <div class="story_estimation">'+story['worktime']+'</div>\n' +
                                '                            <div class="activity_card_operation" style="display: none;">\n' +
                                '                                <img src="../icons/up-arrow.png" class="add_up_icon addStoryUp"/>\n' +
                                '                                <img src="../icons/trash.png" class="remove_icon removeStory"/>\n' +
                                '                                <img src="../icons/down-arrow.png" class="add_right_icon addStoryDown"/>\n' +
                                '                            </div>\n' +
                                '                        </div>';
                        }
                        rdiv+='</div>';
                    }
                    rdiv+='</div></div>';
                }
                rdiv+='</div>';
                rindex = parseInt(i)+1;
            }
            $('.add_release_icon').parent().before(rdiv);

            var actList = $('.activity_card');
            for(var i = 0;i<actList.length;i++){
                var aid = actList.eq(i).attr('data-aid');
                var tList = actList.eq(i).parent().find('.task_card');
                var spanel = $('.story_panel[data-aid='+aid+']');
                if(spanel.length<=0){
                    $('.release_div').each(function () {
                        var s = '<div class="backlog_div"><div class="story_panel" data-aid="'+aid+'" style="margin-left: -5px;"></div></div>';
                        $(this).append(s);
                    });
                }

                for(var j = 0;j<tList.length;j++){
                    var tid = tList.eq(j).attr('data-tid');
                    var sListdiv = $('.story_list[data-tid='+tid+']');
                    if(sListdiv.length<=0){
                        $('.story_panel[data-aid='+aid+']').append('<div class="story_list" data-tid="'+tid+'"><div class="story_plus_card">' +
                            '                                <div style="margin-top: 2px;text-align: center;">' +
                            '                                <img class="add_story" src="../icons/add_large.png" /></div>' +
                            '                                </div>' +
                            '                                 </div>')
                    }
                }
            }

            $('.release_div').each(function () {
                var rh = $(this).height() - 20;
                var s = '<div class="backlog_div">\n' +
                    '                <div class="story_panel" style="height: '+rh+'px;" style="margin-left: -5px;">\n' +
                    '                    <div class="story_plus_card" style="margin-left: 5px;">\n' +
                    '                    </div>\n' +
                    '                </div>\n' +
                    '            </div>';
                $(this).append(s);
            });

        }, error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest.status);
            alert(XMLHttpRequest.readyState);
            alert(textStatus);
        }
    });
}