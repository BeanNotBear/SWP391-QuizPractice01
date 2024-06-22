<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="side-barright">
    <div class="box-container">            
        <h5>Hot new Post</h5> <!-- Hiển thị blogs mới nhất theo thời gian -->
        <div class="box">
            <button> <a href="blogdetail?pid=${nee.blog_id}"> <img src="${nee.getThumbnail()}" id="ima"/></a></button>
            <div class="student">                        
                <h3>    ${nee.getTitle()}</h3>                           
            </div>
            <p id="date-bl">Date: ${nee.createdDate}</p>    
        </div>
    </div>
    <c:forEach items="${newblog}" var="li" >                 
        <div class="box">
            <img src="${li.thumbnail}" id="ima"/>
            <div class="student">
                <div>
                    <h3>${li.title}</h3>
                    <p> ${li.brieinfo}</p>                              
                </div>
            </div>
        </div>
    </c:forEach> 

    <br><!-- Tìm kiếm blogs theo tiêu đề. -->
    <div class="icons">
        <div id="search-btn" class="fas fa-search"> </div>
    </div>
    <h5>Search</h5>           
    <form id="search-blog" action="blog" method="post" class="search-formm" >
        <input id="sea" type="text" name="search"  placeholder=" Search blogs...." maxlength="100" value="${param.search}" >
        <br><!-- comment -->
        <button  id="seb" type="submit" class="fas fa-search" ></button>
    </form>

    <br><!-- Hiển thị tất cả danh mục (category)-->          
    <h5>Category Posts</h5> 
    <ul>
        <c:forEach items="${listcatego}" var="l">
            <li class="catepost ${tag == l.id? "choose":""}"><a href="category?cid=${l.id}">${l.getCategory_Name()}</a></li>                
        </c:forEach>
    </ul>
</div>