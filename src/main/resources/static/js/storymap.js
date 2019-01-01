var aindex = 1;
var tindex = 1;

$(function(){

    $('.add_backlog').on("click",function () {
        $(this).parent().parent().before(createBacklogDiv());
        var aid = aindex-1;
        var tid = tindex-1;

        $('.release_div').each(function () {
            var s = '<div class="story_panel" data-aid="'+aid+'" style="margin-left: -5px;">\n' +
                '<div class="story_list" data-tid="'+tid+'"><div class="story_plus_card">\n' +
                '                        <div style="margin-top: 2px;margin-left: 25px;">\n' +
                '                            <img class="add_story" src="../icons/add_large.png" /></div>\n' +
                '                    </div>\n' +
                '                </div></div>';
            $(this).find('.backlog_div:last').html(s);
            var rh = $(this).height() - 20;
            s = '<div class="backlog_div">\n' +
                '                <div class="story_panel" style="height: '+rh+'px;" style="margin-left: -5px;">\n' +
                '                    <div class="story_plus_card">\n' +
                '                    </div>\n' +
                '                </div>\n' +
                '            </div>';
            $(this).append(s);
        });
    });

    addPerson();
    activityCardBind();
    addActivityCard();
    removeActivityCard();
    modifyActicityCard();
    removeTaskCard();
    addTaskCard();
    taskCardBind();
    storyCardBind();
    addFirstStoryCard();
    removeStoryCard();
    addNextStoryCard();
});

function addPerson() {
    $('body').on("click",'.add_person_card',function (e) {
        $(this).parent().find('.person_panel').append('<img class="person_card next_person_card" src="../icons/man.png" />');
    });
}

function createBacklogDiv() {
    var bdiv = '<div class="backlog_div">\n' +
        '                <div>\n' +
        '                    <div style="display: inline-block;" class="person_panel">\n' +
        '                        <img class="person_card" src=" ../icons/man.png" /></div>\n' +
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
                '                        <div style="margin-top: 2px;margin-left: 25px;">\n' +
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
                '                        <div style="margin-top: 2px;margin-left: 25px;">\n' +
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
        $('#activityModal').modal('show');
    });

    $('#activityModal').on("dblclick",'.modal_content',function () {
        var content = $(this).html();
        var s = '<textarea type="text" class="form-control" rows="13">'+content+'</textarea>\n' +
            '                        <div class="saveActivity"><i class="fa fa-check"></i></div>\n' +
            '                        <div class="cancelModifyAct"><i class="fa fa-close"></i></div>';
        $(this).html(s);
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
        '                    <div style="margin-top: 2px;margin-left: 25px;">\n' +
        '                        <img class="add_story" src="../icons/add_large.png" /></div>\n' +
        '                </div></div>';
    $('.release_div').find(op).append(sc);
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
    var s = '<div class="story_card">\n' +
        '                            <div class="activity_textDiv">text</div>\n' +
        '                            <div class="story_state todo">todo</div>\n' +
        '                            <div class="story_estimation">23</div>\n' +
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
                '                    <div style="margin-top: 2px;margin-left: 25px;">\n' +
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