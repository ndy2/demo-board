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
        data.writerName, data.createDate,data.contents, data.id
    )

    $(".comment-write").prepend(template);
    $(".comment-write textarea").val("");
}

/**
 *  댓글 삭제
 */
// $(".delete-answer-form").click(deleteComment);
$(document).on('click', '.delete-answer-form', deleteComment);

function deleteComment(e){
    e.preventDefault();
    var deleteBtn = $(this);
    var url = $(this).attr("action");
    console.log("url: "+url);

    $.ajax({
        type : 'delete',
        url : url,
        dataType : 'text',
        error : function(xhr,status){
            console.log("error");
            console.log("xhr : "+xhr);
            console.log("status : "+status);
        },
        success : function(data,status){
            console.log("success");
            console.log(data);
            deleteBtn.closest("article").remove();
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