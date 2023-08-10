function login() {
  var emailpassword = {}

  emailpassword.password = $("#password").val();
  emailpassword.email = $("#email").val();

  $.ajax({
    type: "POST",
    url: "http://localhost:8080/api/aluno/login",
    data: JSON.stringify(emailpassword),
    contentType: "application/json; charset=utf-8",
    dataType: "text",
    success: function (data) {
      console.log("entrou!")
      sessionStorage.setItem('email', emailpassword.email);
      sessionStorage.setItem('password', emailpassword.password);
      getUserIDByEmail(emailpassword.email, function(userID){
        sessionStorage.setItem('ID', userID);
      })
      sessionStorage.setItem('validado', true)
      window.location.href = `home.html`;
    },
    error: function (errorThrown) {
      console.error('Login error:', errorThrown);
      alert('Login failed. Please check your email and password.');
    }
  });
}

function logout(){
  sessionStorage.clear();
  window.location.href = `login.html`;
}

function validaUser(id_usuario){
  if(sessionStorage.getItem('validado') != undefined || null) {
    const email = sessionStorage.getItem('email')
    getUserInfoByEmail(email, function(userInfo){
        console.log(userInfo.nome + ' '+ userInfo.sobrenome)
        $("#setUserName").html('<img src="./resources/img/profile.png" class="profile-icon"> <span class="d-none-md d-inlineblock" style="margin-right: 8px"></span> '+ userInfo.nome + ' '+ userInfo.sobrenome)
    });
    if(sessionStorage.getItem('CursoSelectedId') == undefined || null) {
      

      getUserIDByEmail(email, function(userID){
        sessionStorage.setItem('ID', userID);
        getCursoUsuarioByUserID(userID, function(cursos){
          getCursoInfoByID(cursos[0][1], cursos[0][0], function(OBJ){
              setSelectedCurso(OBJ.id_curso, OBJ.nome, OBJ.status_curso)
              $("#fodase").html(OBJ.nome + ' <i class="glyphicon glyphicon-refresh"></i> ')
          })
        })
      });
    } else {
      console.log("Session OK")
      const selectedNome = sessionGet('cursoSelectedNome')
      $("#fodase").html(selectedNome + ' <i class="glyphicon glyphicon-refresh"></i> ')
    }
  } else {
    sessionStorage.clear();
    window.location.href = 'login.html'
    return false
  }
}

function getQueryParams() {
  const params = {};
  const search = window.location.search.substring(1);
  const queryParams = search.split("&");
  for (const param of queryParams) {
    const [key, value] = param.split("=");
    params[key] = decodeURIComponent(value);
  }
  return params;
};


// Lista Disciplina (Menu Superior)
function ListarCursos() {
  const email = sessionStorage.getItem('email')
  getUserIDByEmail(email, function(userid){
    getCursoUsuarioByUserID(userid, function (cursos){
      for(var i = 0; i < cursos.length; i++){
        getCursoInfoByID(cursos[i][1], cursos[i][0], function(OBJ){
          AdicionarLinhaTabela(
            OBJ.id_curso,
            OBJ.nome,
            OBJ.duracao_periodo,
            OBJ.status_curso,
            true
          );
        })
      }
    });
  });
}

function changeCurso(curso_id, curso_nome, curso_status) {
  setSelectedCurso(curso_id, curso_nome, curso_status)
  $("#fodase").html(curso_nome + ' <i class="glyphicon glyphicon-refresh"></i> ')
  window.location.href = window.location.origin + window.location.pathname; 
};

function AdicionarLinhaTabela(id_curso, curso_nome, duracao_periodo, status_curso = null, addBotao = false) {
    var linhaNova = '<tr id="Linha' + id_curso + '">'+
                        '<td>' + curso_nome + '</td>'+
                        '<td class="hidden-sm">' + duracao_periodo + ' anos' + '</td>'+
                        '<td>' + status_curso + '</td>';
    if(addBotao){
      linhaNova += '<td class="btn-col"><a href="#" onclick="javascript:changeCurso(' + id_curso + ', \'' + curso_nome + '\''+', \''+ status_curso +'\');"> Selecionar</a></td>';
    }
      linhaNova += '</tr>'
    $("#ListaCadastro tbody").prepend(linhaNova);
    $('#myModal').modal().hide();
};

function ListarDisciplinas(usuario_id) {
  const curso_id = sessionGet('CursoSelectedId')
    getDisciplinasByCursoID(curso_id, usuario_id, function(disciplinas){
      for(var i = 0; i < disciplinas.length; i++){
        console.log(disciplinas[i].idDisciplina);
        getDisciplinasByUserID(
          disciplinas.usuario_id, 
          disciplinas[i].idDisciplina, 
          disciplinas[i].nome_disciplina, 
          disciplinas[i].profesor, 
          function(OBJ){
            AdicionarLinhaTabelaDisciplinas(
              OBJ.disciplina_id,
              OBJ.disciplina_nome,
              OBJ.disciplina_professor,
              OBJ.status_disciplina,
              OBJ.nota
            );
          }
        );
      }

    });
}

function AdicionarLinhaTabelaDisciplinas(disciplina_id, disciplina_nome, disciplina_professor, status_disciplina, nota) {
  var linhaNova = '<tr id="Linha' + disciplina_id + '">'+
                      '<td>' + disciplina_nome + '</td>'+
                      '<td>' + disciplina_professor + '</td>';
  if(nota != null) {
    linhaNova += '<td>'+ nota +'</td>';
  } else{
    linhaNova += '<td >-</td>'
  }
  if (status_disciplina == 'APROVADO'){
    linhaNova += '<td class="disc-obs aproved">'+ status_disciplina +'</td>';
  } else if (status_disciplina == 'REPROVADO'){
    linhaNova += '<td class="disc-obs reproved">'+ status_disciplina +'</td>';
  } else {
    linhaNova += '<td >'+ status_disciplina +'</td>';
  }
    linhaNova += '</tr>';

  $("#ListarDisciplinas tbody").prepend(linhaNova);
  $('#myModal').modal().hide();
};

function ListarProtocolos(usuario_id, curso_id){
  getProtocoloByUser(usuario_id, curso_id, function(protocolos){
    var OBJ
    for(var i = 0; i < protocolos.length; i++){
        OBJ = {
          "id_protocolo": protocolos[i][0],
          "id_usuario": protocolos[i][9],
          "id_curso": protocolos[i][1],
          "protocolo_typeID" : protocolos[i][8],
          "protocolo_status" : protocolos[i][7],
          "protocolo_etapa" : protocolos[i][5],
          "protocolo_setor" : protocolos[i][6],
          "protocolo_campo" : protocolos[i][2],
          "protocolo_docpath" : protocolos[i][3],
          "protocolo_docreturn" : protocolos[i][4],
          "protocolo_nome": null
        }
        getProtocoloTypeByID(protocolos[i][8], OBJ, function(data){
          AdicionarLinhaTabelaProtocolos(data);
        })
    }
  });
}

function AdicionarLinhaTabelaProtocolos(PROTOCOLO){
  var linhaNova = '<tr id="Linha' + PROTOCOLO.id_protocolo + '">'+
                    '<td style="padding: 18px 8px">' + PROTOCOLO.id_protocolo + '</td>' +
                    '<td style="padding: 18px 8px">' + PROTOCOLO.protocolo_nome + '</td>' +
                    '<td style="width: 80px; text-align: right; padding: 18px 10px">' + PROTOCOLO.protocolo_status + '</td>'+
                    '<td class="btn-col"><a href="#" onClick="javascript:verProtocolo('+ PROTOCOLO.id_protocolo +')">VER</a></td>'
      linhaNova +='</tr>'

  $("#ListarProtocolos tbody").prepend(linhaNova);
  $('#myModal').modal().hide();
}

function verProtocolo(id_protocolo){
  getProtocoloByID(id_protocolo, function(PROTOCOLO){
    getProtocoloTypeByID(PROTOCOLO.protocoloTypeId, PROTOCOLO, function(PROTOCOLO){
      console.log(PROTOCOLO)
      $( "#openProtocolo" ).addClass( "show" );
      $('#protocolo_id').text('Protocolo #'+PROTOCOLO.id_protocolo);
      $('#protocolo_nome').text(PROTOCOLO.protocolo_nome);
      $('#protocolo_status').text(PROTOCOLO.protocoloStatus);
      $('#protocolo_setor').text(PROTOCOLO.protocoloSetor);
      $('#protocolo_etapa').text(PROTOCOLO.protocoloEtapa + '/' + PROTOCOLO.protocoloEtapa);
      $('#protocolo_campo').text(PROTOCOLO.protocoloCampo);
      $('#protocolo_nome').text(PROTOCOLO.protocolo_nome);

      
      if(PROTOCOLO.protocoloDocpath != null){
        $("#protocolo-file" ).addClass( "btn-col" );
        $('#protocolo-file').html('<a href="'+ PROTOCOLO.protocoloDocpath + '" target="_blank">Acessar</a>');
      }
      
      if(PROTOCOLO.protocoloDocreturn != null){
        $("#protocolo-return" ).addClass( "btn-col" );
        console.log('protocoloDocreturn = ' + PROTOCOLO.protocoloDocreturn)
        $('#protocolo-return').html('<a href="'+ PROTOCOLO.protocoloDocreturn + '" target="_blank">Acessar</a>');
      }

      getCursoInfoByID(PROTOCOLO.cursoId, null, function(CURSO){
        $('#protocolo_curso').text(CURSO.nome);
      });

    })
  });
}
function closeProtocolo(){
  $( "#openProtocolo" ).removeClass( "show" );
}

function getSelectedCurso(){
  return {
    "user_id": sessionGet('ID'),
    "curso_id": sessionGet('CursoSelectedId'),
    "curso_nome": sessionGet('cursoSelectedNome'),
    "curso_status": sessionGet('cursoSelectedStatus')
  }
}

function setSelectedCurso(curso_id, curso_nome, curso_status){
  sessionStorage.setItem('CursoSelectedId', curso_id);
  sessionStorage.setItem('cursoSelectedNome', curso_nome);
  sessionStorage.setItem('cursoSelectedStatus', curso_status);
}


function sessionGet(chave){
  return sessionStorage.getItem(chave)
}

// Retorna ID do Usuário através do EMAIL
function getUserIDByEmail(email, callback){
  $.ajax({
      type: "GET",
      url: "http://localhost:8080/api/curso/encontre/" + email,
      contentType: "application/json; charset=utf-8",
      dataType: "json",
      success: function (data) {
        callback(data[0].id_user);
      },
      error: function () {
        console.error("Error loading");
      },
  });
}

// Retorna os Dados do Usuário através do EMAIL
function getUserInfoByEmail(email, callback){
  $.ajax({
      type: "GET",
      url: "http://localhost:8080/api/curso/encontre/" + email,
      contentType: "application/json; charset=utf-8",
      dataType: "json",
      success: function (data) {
        callback(data[0]);
      },
      error: function () {
        console.error("Error loading");
      },
  });
}

function getCursoUsuarioByUserID(id_user, callback){
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/api/curso/curso_usuario/" + id_user,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
          callback(data[0]);
        },
        error: function () {
          console.error("Error loading");
        },
    });
}

function getCursosUsuarioByUserID(id_user, callback){
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/api/curso/cursos_usuario/" + id_user,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
          callback(data);
        },
        error: function () {
          console.error("Error loading");
        },
    });
}


function getCursoStatusByUserCurso(usuario_id, curso_id, callback){
  getCursoUsuarioByUserID(usuario_id, function (cursos){
    for(var i = 0; i < cursos.length; i++){
      if(cursos[i][1] == curso_id){
        callback(cursos[i][0])
      }
    }
    callback(null)
  });
}


function getCursoInfoByID(id, status_curso = null, callback){
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/api/curso/curso_info/" + id,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data) {
          data.id_curso = id;
          if(status_curso != null){
            data.status_curso = status_curso;
          }
          callback(data);
        },
        error: function () {
          console.error("Error loading");
        },
    });
}

function getDisciplinasByCursoID(id, usuario_id = null , callback) {
  $.ajax({
    type: "GET",
    url: "http://localhost:8080/api/disciplina/cursos_disciplinas/" + id,
    contentType: "application/json; charset=utf-8",
    dataType: "json",
    success: function (data) {
      if (usuario_id != null){
        data.usuario_id = usuario_id;
      }
      callback(data);
    },
    error: function () {
      console.error("Error loading");
    },
  });
}

function getAllDisciplinas(callback){
  $.ajax({
      type: "GET",
      url: "http://localhost:8080/api/disciplina/get",
      contentType: "application/json; charset=utf-8",
      dataType: "json",
      success: function (data) {
        callback(data);
      },
      error: function () {
        console.error("Error loading");
      },
  });
}


function getDisciplinasByUserID(usuario_id, disciplina_id, disciplina_nome = null, disciplina_professor = null, callback){
  $.ajax({
    type: "GET",
    url: `http://localhost:8080/api/disciplina/disciplina_usuario/${usuario_id}/${disciplina_id}`,
    contentType: "application/json; charset=utf-8",
    dataType: "json",
    success: function (data) {
      if(disciplina_nome != null){
        data.disciplina_nome = disciplina_nome;
      }
      if(disciplina_professor != null){
        data.disciplina_professor = disciplina_professor;
      }
      callback(data);
    },
    error: function () {
      console.error("Error loading");
    },
  });
}


function getProtocolosByStatus(status_curso, callback){
  $.ajax({
    type: "GET",
    url: `http://localhost:8080/api/protocolo/findByStatusCursoProtocolo/${status_curso}`,
    contentType: "application/json; charset=utf-8", 
    dataType: "json",
    success: function (data) {
      callback(data);
    },
    error: function () {
      console.error("Error loadingn");
    },
  });
}
function getProtocoloTypeByID(protocolo_id, OBJ = null, callback){
  $.ajax({
    type: "GET",
    url: `http://localhost:8080/api/protocolo/protocolo_id/${protocolo_id}`,
    contentType: "application/json; charset=utf-8", 
    dataType: "json",
    success: function (data) {
      if(OBJ != null){
        OBJ.protocolo_nome = data.protocoloNome
        callback(OBJ);
      } else {
        callback(data);
      }
      
    },
    error: function () {
      console.error("Error loadingn");
    },
  });
}


function getProtocoloByUser(usuario_id, curso_id, callback){
  $.ajax({
    type: "GET",
    url: `http://localhost:8080/api/protocoloUser/protocolo_info/${usuario_id}/${curso_id}`,
    contentType: "application/json; charset=utf-8", 
    dataType: "json",
    success: function (data) {
      callback(data);
    },
    error: function () {
      console.error("Error loading");
    },
  }); 
}

function getProtocoloByID(protocolo_id, callback){
  $.ajax({
    type: "GET",
    url: `http://localhost:8080/api/protocoloUser/protocolo_id/${protocolo_id}`,
    contentType: "application/json; charset=utf-8", 
    dataType: "json",
    success: function (data) {
      data.id_protocolo = protocolo_id
      callback(data);
    },
    error: function () {
      console.error("Error loading");
    },
  }); 
}



function postProtocoloUser(usuario_id, curso_id, protocolo_type, protocolo_campo1, protocolo_docname = null){
  $.ajax({
    type: "POST",
    url: "http://localhost:8080/api/protocoloUser/register",
    data: {
      "userId": usuario_id,
      "cursoId": curso_id,
      "protocoloTypeId": protocolo_type,
      "protocoloEtapa": 1,
      "protocoloSetor": "ALUNO",
      "protocoloStatus": "EM PROCESSO",
      "protocoloCampo": protocolo_campo1,
      "protocoloDocpath": usuario_id +'/'+ curso_id + '/' + protocolo_docname
    },
    contentType: "application/json; charset=utf-8",
    dataType: "text",
    success: function (data) {
    },
    error: function (jqXHR, errorThrown) {
      
    }
  });
}
