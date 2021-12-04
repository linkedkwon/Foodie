(function() {
    var tagList = [];

    var renderTagList = function() {
        var i = 0;
        var tagsHtml = '';

        for(; i < tagList.length; i++) {
            tagsHtml += "<div class=\"tag-item btn-secondary btn\">" + tagList[i] + "<a class=\"delete-tag-item\" onClick=\"deleteTag('" + tagList[i] + "\')\">×</a></div>"
        }

        $(".tag-list").html(tagsHtml);
    };


    $("#table-filter9").on("change", function(event) {
        // console.log()
        // const value = event.currentTarget.value;
        const value = $('#table-filter9 option:checked').text() + '역'
        if (tagList.indexOf(value) >= 0) {
            $('#table-filter9 option:eq(0)').prop('selected', true);
            return;
        }

        tagList.push(value);
        renderTagList()
        $('#table-filter9 option:eq(0)').prop('selected', true);
    });

    // 지도검색
    $("#restaurants-address-detial").on("click", function() {

        $('#address-modal').modal({
            keyboard: false
        })
    });

    $('#address-modal').on("shown.bs.modal", function() {
        $('#address-modal').find(".modal-body").html("<div id=\"daum-map-container\"></div>")
        var $this = $(this);
        var $mapContainer = $("#daum-map-container");
        var $input = $("#restaurants-address-detial");
        var $roadInput = $("#restaurants-address-detial-jibun");
        new daum.Postcode({
            oncomplete: function(data) {
                var jibunAddress = data.jibunAddress;
                var roadAddress = data.roadAddress;
                var sido = data.sido;
                var sigungu = data.sigungu;
                var dong = data.bname;

                $input.val(roadAddress);
                $roadInput.val(jibunAddress);
                // $roadInput.text(jibunAddress);
                $this.modal("hide");
            },
            width : '100%',
        }).embed($mapContainer.get(0));
    });


    $('#address-modal').on("hidden.bs.modal", function() {
        $("#daum-map-container").remove();
    })

    // 지도검색
    $("#search_list_detail").on("click", function() {
        console.log("click")
        $('#search_list').modal({
            keyboard: false
        })
    });

    $('#search_list').on("shown.bs.modal", function() {
        $('#search_list').find(".modal-body").html("<div id=\"daum-map-container\"></div>")
        var $this = $(this);
        var $mapContainer = $("#daum-map-container");
        var $input = $("#restaurants-address-detial");

        new daum.Postcode({
            oncomplete: function(data) {
                var jibunAddress = data.jibunAddress;
                var roadAddress = data.roadAddress;
                var sido = data.sido;
                var sigungu = data.sigungu;
                var dong = data.bname;

                $input.val(roadAddress);

                $this.modal("hide");
            },
            width : '100%',
        }).embed($mapContainer.get(0));
    });


    $('#search_list').on("hidden.bs.modal", function() {
        $("#daum-map-container").remove();
    })


    // init summernote Wyswig
    $('#summernote').summernote({
        placeholder: 'History를 입력해주세요.',
        height: 300,
        toolbar: [
            ['style', ['style']],
            ['font', ['bold', 'underline', 'clear']],
            ['color', ['color']],
            ['para', ['ul', 'paragraph']],
            ['insert', ['link']],
        ]
    })

    $('.summernote').summernote({
        placeholder: 'Menu 를 입력해주세요.',
        height: 300,
        toolbar: [
            ['style', ['style']],
            ['font', ['bold', 'underline', 'clear']],
            ['color', ['color']],
            ['para', ['ul', 'paragraph']],
            ['insert', ['link']],
        ]
    })
})()