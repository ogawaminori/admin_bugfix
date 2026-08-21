// 画像選択時にfile名出力
$(function() {
    $('#image').on('change', function (e) {
        var fileName = e.target.files[0] ? e.target.files[0].name : '選択されていません';
        $('#file-name').text(fileName);
    });
});
