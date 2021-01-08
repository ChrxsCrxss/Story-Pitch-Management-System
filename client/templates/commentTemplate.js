const createCommentCard = (comment) =>
    `<div class="comment-card" class="card-body">
        <strong>${comment.commentorId} @${comment.creationTime}</strong>
        <p>${comment.content}</p>
    </div> 
    <hr>   
    `; 
