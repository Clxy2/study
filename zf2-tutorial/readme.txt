==== PHP Zend Framework 2 on Netbeans ===

-- eclipse
    PDT和XDebug安装后，还是不能启动及调试。
    也许走Apache调用的路线可以？

-- php.ini
    = 修改后不会马上生效。
    extension_dir = "D:\php\php-5.5.7-x86\ext" ;指定扩展包目录
    extension="D:\php\php-5.5.7-x86\ext\php_mysqli.dll" ;指定MySQL驱动。MariaDB通用。可以不加绝对路径？
    extension="D:\php\php-5.5.7-x86\ext\php_pdo_mysql.dll"
    extension=php_openssl.dll ;指定SSL支持。
    date.timezone = Asia/Chongqing ;指定市区。默认是UTC。
    及下面的[XDebug]

-- MariaDB
    按MySQL方式使用。驱动，PHP设置等都无需替换。

-- Enable XDebug
    PHP.ini中追加以下配置。
    [XDebug]
    zend_extension = "D:\php\php-5.5.7-x86\extras\php_xdebug-2.2.3-5.5-vc11-nts.dll"
    xdebug.remote_enable=on
    xdebug.remote_host=localhost
    xdebug.remote_handler=dbgp
    xdebug.remote_port=9000
