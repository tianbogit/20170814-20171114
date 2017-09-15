<html lang="en">
<head>
<meta charset="gbk">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Spider</title>
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-md-12 mt-1">
            <#list context.titles>
                <h2>爬取内容:</h2>
                <ul>
                    <#items as title>
                        <li>${title}</li>
                    </#items>
                </ul>
            <#else>
                <p>The content is currently empty!</p>
            </#list>
            </div>

        </div>
    </div>
</body>
</html>