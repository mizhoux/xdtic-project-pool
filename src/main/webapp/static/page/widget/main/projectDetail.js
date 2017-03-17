/**
 * Created by chenpeng on 2017/3/17.
 */
'use strict';

var multilineContent = {
    render: function render(createElement) {
        var lines = this.content.split('\n');
        var len = lines.length;
        var linesNew = [];

        while (len--) {
            linesNew.unshift(lines[len], 'br');
        }
        linesNew.pop();

        return createElement('p', linesNew.map(function (line) {
            if (line === 'br') {
                return createElement(line);
            } else {
                return createElement('span', line);
            }
        }));
    },
    props: {
        content: {
            require: true,
            type: String
        }
    }
};

var detailProjectMain = new Vue({
    el: '#detailProjectMain',
    data: {
        project: projectInfo
    },

    components: {
        'multiline-content': multilineContent
    }
});