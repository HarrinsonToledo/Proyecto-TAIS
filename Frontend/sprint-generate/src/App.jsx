import { useState } from 'react'
import XMLValidator from './components/XMLValidator';
import './App.css'

function App() {
  const [springSelect, setSpringSelect] = useState('3.3.2 (SNAPSHOT)');
  const [javaSelect, setJavaSelect] = useState('Java 22');

  const [grupo, setGrupo] = useState("com.example")
  const [artefacto, setArtefacto] = useState("demo")
  const [nombre, setNombre] = useState("demo")
  const [descripcion, setDescripcion] = useState("Proyecto Demo para Sprint Boot")
  const [paquete, setPaquete] = useState(grupo + "." + artefacto)

  const [jsonData, setJsonData] = useState({ key1: 'value1', key2: 'value2' });

  const changeSpringSelect = (event) => {
    setSpringSelect(event.target.value);
  };

  const changeJavaSelect = (event) => {
    setJavaSelect(event.target.value);
  };

  const InputGrupoChange = (event) => {
    setGrupo(event.target.value)
    setPaquete(event.target.value + "." + artefacto)
  }

  const InputArtefactoChange = (event) => {
    setArtefacto(event.target.value)
    setPaquete(grupo + "." + event.target.value)
  }

  const InputNombreChange = (event) => {
    setNombre(event.target.value)
  }

  const InputDescripcionChange = (event) => {
    setDescripcion(event.target.value)
  }

  const InputPaqueteChange = (event) => {
    setPaquete(event.target.value)
  }

  const onFileChange = (event) => {
    setSelectedFile(event.target.files[0]);
  };

  const onFileUpload = () => {
    const formData = new FormData();
    formData.append('file', selectedFile);
    formData.append('jsonData', JSON.stringify(jsonData));

    fetch('URL_DE_TU_BACKEND', {
      method: 'POST',
      body: formData,
    })
      .then(response => response.json())
      .then(data => {
        console.log('Respuesta del servidor:', data);
      })
      .catch(error => {
        console.error('Hubo un error al subir el archivo:', error);
      });
  };

  return (
    <div className='container'>
      <p className='titleP colortxt'><b>Generador de Api's de Spring Boot</b></p>
      <div className='description borde-b-w'>
        <p className='titleS'><b>Especificaciones Principales</b></p>
        <p><b className='colortxt'>Proyecto</b> - Maven</p>
        <p><b className='colortxt'>Lenguaje</b> - Java</p>
      </div>
      <div className='options borde-b-w'>
        <p className='titleS'><b>Opciones Generales</b></p>
        <section className='sect'>
          <label className='c-radio'>
            3.3.2 (SNAPSHOT)
            <input
              name='v-spring'
              type="radio"
              value="3.3.2 (SNAPSHOT)"
              checked={springSelect === "3.3.2 (SNAPSHOT)"}
              onChange={changeSpringSelect}
            />
            <span className="checkmark"></span>
          </label>
          <label className='c-radio'>
            3.3.1
            <input
              name='v-spring'
              type="radio"
              value="3.3.1"
              checked={springSelect === "3.3.1"}
              onChange={changeSpringSelect}
            />
            <span className="checkmark"></span>
          </label>
          <label className='c-radio'>
            3.2.8 (SNAPSHOT)
            <input
              name='v-spring'
              type="radio"
              value="3.2.8 (SNAPSHOT)"
              checked={springSelect === "3.2.8 (SNAPSHOT)"}
              onChange={changeSpringSelect}
            />
            <span className="checkmark"></span>
          </label>
          <label className='c-radio'>
            3.2.7
            <input
              name='v-spring'
              type="radio"
              value="3.2.7"
              checked={springSelect === "3.2.7"}
              onChange={changeSpringSelect}
            />
            <span className="checkmark"></span>
          </label>
        </section>
        <section className='sect'>
          <label className='c-radio'>
            Java 22
            <input
              name='v-java'
              type="radio"
              value="Java 22"
              checked={javaSelect === "Java 22"}
              onChange={changeJavaSelect}
            />
            <span className="checkmark"></span>
          </label>
          <label className='c-radio'>
            Java 21
            <input
              name='v-java'
              type="radio"
              value="Java 21"
              checked={javaSelect === "Java 21"}
              onChange={changeJavaSelect}
            />
            <span className="checkmark"></span>
          </label>
          <label className='c-radio'>
            Java 17
            <input
              name='v-java'
              type="radio"
              value="Java 17"
              checked={javaSelect === "Java 17"}
              onChange={changeJavaSelect}
            />
            <span className="checkmark"></span>
          </label>
        </section>
      </div>
      <div className='form-data borde-b-w'>
        <p className='titleS'><b>Metadata Proyecto</b></p>
        <section className='individual'>
          <p className='in-label'>Grupo</p>
          <input type="text" value={grupo} onChange={InputGrupoChange} className='in-txt' />
        </section>
        <section className='individual'>
          <p className='in-label'>Artefacto</p>
          <input type="text" value={artefacto} onChange={InputArtefactoChange} className='in-txt' />
        </section>
        <section className='individual'>
          <p className='in-label'>Nombre</p>
          <input type="text" value={nombre} onChange={InputNombreChange} className='in-txt' />
        </section>
        <section className='individual'>
          <p className='in-label'>Descripción</p>
          <input type="text" value={descripcion} onChange={InputDescripcionChange} className='in-txt' />
        </section>
        <section className='individual'>
          <p className='in-label'>Nombre Paquete</p>
          <input type="text" value={paquete} onChange={InputPaqueteChange} className='in-txt' />
        </section>
      </div>
      <div className='xml-data'>
        <p className='titleS'><b>Metamodelo XML</b></p>
        <input type="file" onChange={onFileChange} />
        <button onClick={onFileUpload}>
          Subir Archivo
        </button>
      </div>
    </div>
  )
}

export default App
