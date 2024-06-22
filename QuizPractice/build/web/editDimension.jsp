<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Dimension</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <h2>Edit Dimension</h2>
        <form action="editDimension" method="post">
            <input type="hidden" name="dimensionId" value="${dimension.dimensionId}" />
            <div class="form-group">
                <label for="type">Type:</label>
                <input type="text" class="form-control" id="type" name="type" value="${dimension.type}"  />
            </div>
            <div class="form-group">
                <label for="dimensionName">Dimension Name:</label>
                <input type="text" class="form-control" id="dimensionName" name="dimensionName" value="${dimension.dimensionName}" />
            </div>
            
            <div class="form-group">
                <label for="dimensionName">Dimension Description</label>
                <input type="text" class="form-control" id="description" name="description" value="${dimension.getDescription()}" />
            </div>
            
            <button type="submit" class="btn btn-primary">Save Changes</button>
            <a href="javascript:history.go(-1);" class="btn btn-secondary">Cancel</a>
        </form>
    </div>
</body>
</html>