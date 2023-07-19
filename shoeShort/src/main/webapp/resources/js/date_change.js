function is_yun(y) {
	if(((y % 4) == 0 && (y % 100) != 0) || (y % 400) == 0)
		return true;
	else
		return false;
}

function resetday(y, m, d) {
	monthdays = new Array(12);
	monthdays[0] = 31;
	if(is_yun(eval(y)))	monthdays[1] = 29;
	else					monthdays[1] = 28;
	monthdays[2] = 31;	monthdays[3] = 30;	monthdays[4] = 31;	monthdays[5] = 30;	monthdays[6] = 31;
	monthdays[7] = 31;	monthdays[8] = 30;	monthdays[9] = 31;	monthdays[10] = 30;	monthdays[11] = 31;

	var i=0;
	var	del_idx;
	if(d.options.length < monthdays[eval(m)-1]) {
		var NewOpt = Array(3);
		while(d.options.length < monthdays[eval(m)-1]) {
			NewOpt[i] = document.createElement('option');
			d.options.add(NewOpt[i]);
			i++;
		}
	} else if(d.options.length > monthdays[eval(m)-1]) {
		while(d.options.length > monthdays[eval(m)-1]) {
			del_idx = d.options.length - 1;
			d.options.remove(del_idx);
		}
	}
	var	dvalue;
	for(i=1; i<=monthdays[eval(m)-1]; i++) {
		if(i<10)	dvalue = "0"+i;
		else		dvalue = i;
		d.options[i-1].value = dvalue;
		d.options[i-1].text = dvalue;
	}
}