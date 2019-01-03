var aindex = 1;
var tindex = 1;
var sindex = 1;
var rindex = 1;
var edit_activity = false;
var edit_task = false;

$(function(){
    $('.add_backlog').on("click",function () {
        $(this).parent().parent().before(createBacklogDiv());
        var aid = aindex-1;
        var tid = tindex-1;

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
    });

    personCardBind();
    addPerson();
    removePersonCard();
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

        $('.activity_card[data-aid='+aid+']').parent().find('.person_panel').append('<div class="person_card" data-name="'+personName+'" title="'+personName+'">\n' +
            '                            <img src="../portraits/'+imgpath+'.png" width="30" height="30" class="person_img"/>\n' +
            '                            <div class="person_card_operation" style="display: none;">\n' +
            '                                <img src="../icons/trash.png" class="remove_person_icon removePerson"/>\n' +
            '                            </div>\n' +
            '                        </div>');
        $('#addPersonModal').modal('hide');
    });
}

function removePersonCard() {
    $('body').on("click",'.removePerson',function () {
        $(this).parent().parent().remove();

    });
}

function createBacklogDiv() {
    var bdiv = '<div class="backlog_div">\n' +
        '                <div>\n' +
        '<div style="display: inline-block;" class="person_panel">\n' +
        '                        <div class="person_card" data-name="Common" title="Common">\n' +
        '                            <img src="../portraits/4.png" width="30" height="30" class="person_img"/>\n' +
        '                            <div class="person_card_operation" style="display: none;">\n' +
        '                                <img src="../icons/trash.png" class="remove_person_icon removePerson"/>\n' +
        '                            </div>\n' +
        '                        </div>\n' +
        '                    </div>' +
        '                    <img class="add_person_card" src="../icons/add_large.png" />\n' +
        '                </div>\n' +
        '                <div class="activity_card" data-aid="'+aindex+'">\n' +
        '                    <div class="activity_textDiv">text</div>\n' +
        '                    <div class="activity_estimation"></div>\n' +
        '                    <div class="activity_card_operation" style="display: none;">\n' +
        '                    <img src="../icons/left-arrow.png" class="add_left_icon addActivityLeft"/>\n' +
        '                    <img src="../icons/trash.png" class="remove_icon removeActivity"/>\n' +
        '                    <img src="../icons/right-arrow.png" class="add_right_icon addActivityRight"/>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '                <div style="margin-left: -5px;">\n' +
        '                    <div class="task_card" data-tid="'+tindex+'">\n' +
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
    aindex += 1;
    tindex += 1;
    return bdiv;
}
             
function addActivityCard() {
    $('body').on("click",'.addActivityLeft',function () {
        var aid_old = $(this).parent().parent().attr('data-aid');
        $(this).parent().parent().parent().before(createBacklogDiv());
        var aid = aindex-1;
        var tid = tindex-1;

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
    });

    $('body').on("click",'.addActivityRight',function () {
        var aid_old = $(this).parent().parent().attr('data-aid');
        $(this).parent().parent().parent().after(createBacklogDiv());
        var aid = aindex-1;
        var tid = tindex-1;

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
        $(this).parent().parent().parent().remove();
        var op = '.story_panel[data-aid='+aid+']';
        $(op).parent().remove();
    });
}

function modifyActicityCard() {
    $('body').on("dblclick",'.activity_card',function () {
        var aid = $(this).attr('data-aid');
        var content = $(this).find('.activity_textDiv').html();
        $('#activityModal').find('.modal_content').html(content);
        $('#activityModal').attr('data-aid',aid);

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
        $('.activity_card[data-aid='+aid+']').find('.activity_textDiv').html(content);
        $('#activityModal').find('.modal_content').html(content);
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

function createTaskCard() {
    var tcard = '<div class="task_card" data-tid="'+tindex+'">\n' +
        '                        <div class="activity_textDiv">text</div>\n' +
        '                        <div class="task_estimation"></div>\n' +
        '                        <div class="activity_card_operation" style="display: none;">\n' +
        '                            <img src="../icons/left-arrow.png" class="add_left_icon addTaskLeft"/>\n' +
        '                            <img src="../icons/trash.png" class="remove_icon removeTask"/>\n' +
        '                            <img src="../icons/right-arrow.png" class="add_right_icon addTaskRight"/>\n' +
        '                        </div>\n' +
        '                    </div>';
    tindex+=1;
    return tcard;
}

function addTaskCard() {
    $('body').on("click",'.addTaskLeft',function () {
        $(this).parent().parent().before(createTaskCard());
        $(this).parent().parent().parent().find('.task_card').find('.removeTask').show();

        var aid = $(this).parent().parent().parent().parent().find('.activity_card').attr('data-aid');
        var tid = tindex-1;
        addStoryPlusCard(aid,tid);
    });
    $('body').on("click",'.addTaskRight',function () {
        $(this).parent().parent().after(createTaskCard());
        $(this).parent().parent().parent().find('.task_card').find('.removeTask').show();

        var aid = $(this).parent().parent().parent().parent().find('.activity_card').attr('data-aid');
        var tid = tindex-1;
        addStoryPlusCard(aid,tid);
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

function createStoryCard() {
    var s = '<div class="story_card" data-sid="'+sindex+'">\n' +
        '                            <div class="activity_textDiv">text</div>\n' +
        '                            <div class="story_state todo">Todo</div>\n' +
        '                            <div class="story_estimation"></div>\n' +
        '                            <div class="activity_card_operation" style="display: none;">' +
        '                            <img src="../icons/up-arrow.png" class="add_up_icon addStoryUp"/>' +
        '                            <img src=" ../icons/trash.png" class="remove_icon removeStory"/>\n' +
        '                            <img src="../icons/down-arrow.png" class="add_right_icon addStoryDown"/>\n' +
        '                        </div>' +
        '                        </div>';
    sindex+=1;
    return s;
}

function addFirstStoryCard() {
    $('body').on("click",'.add_story',function () {
        $(this).parent().parent().parent().html(createStoryCard());
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
        $(this).parent().parent().after(createStoryCard());
    });

    $('body').on("click",'.addStoryUp',function () {
        $(this).parent().parent().before(createStoryCard());
    });
}

function modifyStoryCard() {
    $('body').on("dblclick",'.story_card',function () {
        var sid = $(this).attr('data-sid');
        var estimation = $(this).find('.story_estimation').html();
        var state = $(this).find('.story_state').html();
        var content = $(this).find('.activity_textDiv').html();

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
        $(this).parent().before('<div style="margin-top: 15px;" class="release_label" data-rid="'+rindex+'"><span>——Release '+(rindex+1) +
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
        for(var i = 0;i<rindex;i++){
            var rid_tmp_old = rList.eq(i).attr('data-rid');
            rList.eq(i).attr('data-rid',i);
            $('.release_label[data-rid='+rid_tmp_old+']').find('span').text('——Release '+(i+1));
        }
        if(rindex==1){
            $('.remove_release').hide();
        }
    });
}