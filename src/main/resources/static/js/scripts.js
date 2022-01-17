/**
 *  댓글 작성
 */
$(".comment-write input[type=submit]").click(addComment);

function addComment(e) {
    e.preventDefault();

    var queryString = $(".comment-write").serialize();
    console.log("query : " + queryString);

    var url = $(".comment-write").attr("action");
    $.ajax({
        type : 'post',
        url :url,
        data : queryString,
        dataType : 'json',
        error : onError,
        success : onSuccess
    });
}

function onSuccess(data,status){
    console.log(data);
    var commentTemplate = $("#answerTemplate").html();
    var template = commentTemplate.format(
        data.writerName, data.createdDate,data.contents, data.id
    )

    $(".comment-write").prepend(template);     //댓글 추가 - 위치 이상함...
    $(".comment-write textarea").val("");      //댓글 작성 칸 비우기
}

/**
 *  댓글 삭제
 */
$(document).on('click', '.delete-answer-form', deleteComment);

function deleteComment(e){
    e.preventDefault();
    var deleteBtn = $(this);
    var url = $(this).attr("action");
    console.log("url: "+url);

    $.ajax({
        type : 'delete',
        url : url,
        dataType : 'json',
        error : function(xhr,status){
            console.log("error");
        },
        success : function(data,status){
            console.log(data);
            if(data.valid){
                deleteBtn.closest("article").remove();  //댓글 삭제
            }else{
                alert(data.errorMessage);               //권한이 없는경우
            }


        }
    })

}

/**
 * 게시글 추천
 */
$(".vote_up").click(recommend_vote);

function recommend_vote(e){
    e.preventDefault();

    var url = $(this).attr("href");
    var reccomend_cnt_compoennt =  $(this).next();
    var cur_reccomend_cnt = reccomend_cnt_compoennt.val();
    console.log(reccomend_cnt_compoennt);
    console.log(cur_reccomend_cnt);

    $.ajax({
        type : 'post',
        url : url,
        dataType : 'json',
        error : function(xhr,status){
            console.log("error");
        },
        success : function(data,status){
            console.log(data);
            if(data.valid){
                console.log("성공!")  //게시글 추천
                reccomend_cnt_compoennt.text(cur_reccomend_cnt+1);
            }else{
                alert(data.errorMessage); //추천을 이미 한 경우
            }
        }
    })

}




/**
 *  유틸리티 함수
 * */
function onError(request,status,error){
    alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
}

String.prototype.format = function() {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function(match, number) {
        return typeof args[number] != 'undefined' ? args[number] : match;
    });
};