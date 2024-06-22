function openRightSideBar () {
    const btn = document.querySelector(".side-barright");
    if(btn.style.display === "block") {
        btn.style.display = "none";
    } else {
        btn.style.display = "block";
    }
}