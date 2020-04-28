document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
        height: 450,
        plugins: [ 'interaction', 'dayGrid', 'timeGrid', 'list'],
        header: {
          left: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek',
          center: 'title',
          right: 'prev,next'
        },
        selectable: true,
        events: [
            { // this object will be "parsed" into an Event Object
                title: 'The First', // a property!
                start: '2020-04-14', // a property!
                end: '2020-04-15' // a property! ** see important note below about 'end' **
            },
            { // this object will be "parsed" into an Event Object
                title: 'https://fullcalendar.io/docs/event-source-object', // a property!
                start: '2020-04-18T17:30:00', // a property!
                end: '2020-04-18T22:00:00' // a property! ** see important note below about 'end' **
            },
            {
                title: 'https://fullcalendar.io/docs/external-dragging-demo',
                start: '2020-04-16T08:30:00', // a property!
                end: '2020-04-16T22:00:00' // a property! ** see important note below about 'end' **
            },
            {
                title: 'payed leave',
                start: '2020-04-01',
                end: '2020-04-08',
                rendering: 'background'
            }
          ]
    });

    calendar.render();
  });
