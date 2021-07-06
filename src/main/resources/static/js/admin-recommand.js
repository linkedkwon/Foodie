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

    var deleteTag = function(value) {
        var index = tagList.indexOf(value);

        if (index < 0) {
            return;
        }

        tagList.splice(index, 1);
        renderTagList();
    };

    $("#select-tags").on("change", function(event) {
        const value = event.currentTarget.value;

        if (tagList.indexOf(value) >= 0) {
            $('#select-tags option:eq(0)').prop('selected', true);
            return;
        }

        tagList.push(value);
        renderTagList()
        $('#select-tags option:eq(0)').prop('selected', true);
    });

    // 지도검색
    $("#search_list_detail").on("click", function() {
        console.log("click")
        $('#search_list').modal({
            keyboard: false
        })

        var val = $( '#table-filter1 option:selected').val();
        console.log('value'+ val)
        $('#example3').dataTable().fnClearTable();
        $.ajax({
            type:"get",
            url:"/admin/recommand/main/regionInfo/" + val,
            datatype: "json",
            success: function(data) {
                if(data.data.length > 0) {
                    var response_data = [];
                    for (step = 0; step < data.data.length; step++) {
                        // Runs 5 times, with values of step 0 through 4.
                        response_data.push(data.data[step])
                    }
                    console.log(response_data)
                    // $('#example tbody tr').replaceWith(data);
                    // $('#payload tr').remove();
                    // $('#payload').html(data);
                    $('#example3').dataTable().fnAddData(response_data);
                    // $.each(data, function(key, value) {
                    //     $("#example tbody").append("<tr>" +
                    //         "<td> hihihii </td>" +
                    //         "<td> hihihii </td>" +
                    //         "<td> hihihii </td>" +
                    //         "<td> hihihii </td>" +
                    //         "<td> hihihii </td>" +
                    //         "</tr>");
                    // });

                } else {
                    return;
                }
            },

            error: function(x, o, e) {
                var msg = "페이지 호출 중 에러 발생 \n" + x.status + " : " + o + " : " + e;
                alert(msg);
            }
        });
    });

    $('#search_list').on("shown.bs.modal", function() {
        // $('#search_list').find(".modal-body").html("<div id=\"daum-map-container\"></div>")
        // var $this = $(this);
        // var $mapContainer = $("#detail_list");
        // var $input = $("#restaurants-address-detial");

        // new daum.Postcode({
        //     oncomplete: function(data) {
        //         var jibunAddress = data.jibunAddress;
        //         var roadAddress = data.roadAddress;
        //         var sido = data.sido;
        //         var sigungu = data.sigungu;
        //         var dong = data.bname;
        //
        //         $input.val(roadAddress);
        //         $this.modal("hide");
        //     },
        //     width : '100%',
        // }).embed($mapContainer.get(0));
    });


    $('#search_list').on("hidden.bs.modal", function() {
        $("#restaurants-address-detial").remove();
    })

})()