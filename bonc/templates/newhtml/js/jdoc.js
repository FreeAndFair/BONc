

function setup() {
  $j('#search-box').val('Search...');
  
  var initialHash = $j.history.getCurrent();
  if (!processHash(initialHash)) {
	  $j('#main-display').load('all-classes.html');
  }
  $j(document).history(function(e,curr,prev) { processHash(curr); });

  var autocompleter = new Autocompleter.Local('search-box', 'search-results', elements_list, {updateElement: selectedAuto, partialChars: 1, fullSearch: true});
  
  $j('#search-box').focus(function(event){
    $('search-box').morph('width: 500px; font-size: 20px;', {duration: 0.2});
    $('search-pane').morph('width: 500px; font-size: 20px;', {duration: 0.2});
    $('main-display').morph('opacity: 0.1', {duration: 0.2});
    $('side-bar').morph('opacity: 0.3', {duration: 0.2});
    if ($j('#search-box').val() == 'Search...') {
      $j('#search-box').val('');
    } else if ($j('#search-box').val() != '') {
      setTimeout(function(){ autocompleter.show(); }, 250);
    }
  });
  
  //On search box lose focus, restore search box (and  small size
  $j('#search-box').blur(function(event){
    $('search-box').morph('width: 150px; font-size: 12px;', {duration: 0.2});
    $('search-pane').morph('width: 200px; font-size: 20px;', {duration: 0.2});
    $('main-display').morph('opacity: 1', {duration: 0.2});
    $('side-bar').morph('opacity: 1', {duration: 0.2});
    if ($j('#search-box').val() == '') {
      $j('#search-box').val('Search...');
    }
  });
  
  $j(document).bind('keydown', {combi: '/', disableInInput: true}, function() {
    $j('#search-box').focus();
    return false;
  });
  
  $j(document).bind('keydown', 'esc', function() {
    $j('#search-box').blur();
  });

  $j(document).bind('keydown', 's', function() {
    $j('#showallspecslink').click();
  });

};

function selectedAuto(selectedElement) {
  var value = Element.collectTextNodesIgnoreClass(selectedElement, 'informal');
  userLoadClass(value);
  $j('#search-box').blur(); 
}

function processHash(hash) {
  var parts = hash.split(':');
  if (parts.length >= 2) {
	if (parts[0] == 'class') {
      loadClass(parts[1]);
	}
    return true;
  }
  return false;
}

function userLoadClass(qualifiedClassname) {
 $j.history.add('class:' + qualifiedClassname);
 loadClass(qualifiedClassname);
}

function loadClass(qualifiedClassname) {
  $j('#main-display').load(qualifiedClassname + '.html');
  $j('#related').load(qualifiedClassname + '-related.html');
}

function navTo(loc) {
  var parts = loc.split(':');
  if (parts.length >= 2) {
    if (parts[0] == 'class') {
      userLoadClass(parts[1]);
    }
  }
  return false;
}

function toggleShowSpecs(link,id) {
  $j(id).toggleClass('invisible')
  if ($j(id).hasClass('invisible')) {
    $j(link).text('Show specs');
  } else {
    $j(link).text('Hide specs');
  }
  return false;
}

function flipVisibility(id) {
  $j(id).toggleClass('invisible');
  return false;
}

function showAllSpecs() {
  $j('#showallspecslink').text('Hide all specs');
  $j('.showspecslink').text('Hide specs');
  $j('.specsdiv').removeClass('invisible');
}

function hideAllSpecs() {
  $j('#showallspecslink').text('Show all specs');
  $j('.showspecslink').text('Show specs');
  $j('.specsdiv').addClass('invisible');
}

function toggleAllSpecs() {
  if ($j('#showallspecslink').text() == 'Show all specs') {
    showAllSpecs();
  } else {
    hideAllSpecs();  
  }
  return false;
}