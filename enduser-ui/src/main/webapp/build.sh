webpack && \
echo 'Copying app bundle...' && \
cp ./bundle/app.js ~/Oracle/Middleware/Oracle_Home/user_projects/domains/base_domain/servers/AdminServer/tmp/_WL_user/enduser-ear-0.0.1-SNAPSHOT/f4hcnd/war/bundle && \
echo 'Copying asset bundle...' && \
cp ./bundle/asset.js ~/Oracle/Middleware/Oracle_Home/user_projects/domains/base_domain/servers/AdminServer/tmp/_WL_user/enduser-ear-0.0.1-SNAPSHOT/f4hcnd/war/bundle/ && \
echo 'Copying string resources...' && \
cp ./res/strings.js ~/Oracle/Middleware/Oracle_Home/user_projects/domains/base_domain/servers/AdminServer/tmp/_WL_user/enduser-ear-0.0.1-SNAPSHOT/f4hcnd/war/res
