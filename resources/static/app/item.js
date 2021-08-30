(function ($) {

    let indexBtn =[]; // 인덱스 버튼
    let pagination = {
        total_pages : 0,
        total_elements : 0,
        current_page : 0,
        current_elements : 0
    }

    //페이지 정보
    let showPage = new Vue({
        el : '#showPage',
        data : {
            totalElements : {},
            currentPage : {},

        }
    });

    //데이터 리스트
    let itemList = new Vue({
        el : '#itemList',
        data : {
            itemList : {}
        }
    });

    searchStart(0);
    // $(function(){
    //     searchStart(0);
    // });




    // 제일 먼저 실행되는 곳 ( html이 다 보이면 동작 )
    // $(document).ready(function () {
    //      searchStart(0)
    //  });


    function searchStart(index) {
        console.log("index : " + index);
        $.get("/api/item?page="+index, function (response){ //  $.get => ajax
            console.dir(response);

            indexBtn = [];
            pagination = response.pagination;

            // 전체 페이지
            showPage.totalElements = pagination.currentElements; // 실제 데이터를 랜더링 될 곳
            showPage.currentPage = pagination.currentPage + 1; // 첫페이지 : 0 + 1

            // 검색 데이터
            itemList.itemList = response.data;



        });
    }
})(jQuery);

