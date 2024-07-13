import { useState } from 'react'
import './App.css'

function App() {
  const [grupo, setGrupo] = useState("com.example")
  const [artefacto, setArtefacto] = useState("demo")
  const [nombre, setNombre] = useState("demo")
  const [descripcion, setDescripcion] = useState("Proyecto Demo para Sprint Boot")
  const [paquete, setPaquete] = useState(grupo + "." + artefacto)

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
          <label className='c-radio'>3.3.2 (SNAPSHOT)
            <input name='v-spring' type="radio"/>
              <span class="checkmark"></span>
          </label>
          <label className='c-radio'>3.3.1
            <input name='v-spring' type="radio"/>
              <span class="checkmark"></span>
          </label>
          <label className='c-radio'>3.2.8 (SNAPSHOT)
            <input name='v-spring' type="radio"/>
              <span class="checkmark"></span>
          </label>
          <label className='c-radio'>3.2.7
            <input name='v-spring' type="radio"/>
              <span class="checkmark"></span>
          </label>
        </section>
        <section className='sect'>
        <label className='c-radio'>Java 22
            <input name='v-java' type="radio"/>
              <span class="checkmark"></span>
          </label>
          <label className='c-radio'>Java 21
            <input name='v-java' type="radio"/>
              <span class="checkmark"></span>
          </label>
          <label className='c-radio'>Java 17
            <input name='v-java' type="radio"/>
              <span class="checkmark"></span>
          </label>
        </section>
      </div>
      <div className='form-data'>
      <p className='titleS'><b>Metadata Proyecto</b></p>
        <section className='individual'>
          <p className='in-label'>Grupo</p>
          <input type="text" value={grupo} onChange={InputGrupoChange} className='in-txt'/>
        </section>
        <section className='individual'>
          <p className='in-label'>Artefacto</p>
          <input type="text" value={artefacto} onChange={InputArtefactoChange} className='in-txt'/>
        </section>
        <section className='individual'>
          <p className='in-label'>Nombre</p>
          <input type="text" value={nombre} onChange={InputNombreChange} className='in-txt'/>
        </section>
        <section className='individual'>
          <p className='in-label'>Descripción</p>
          <input type="text" value={descripcion} onChange={InputDescripcionChange} className='in-txt'/>
        </section>
        <section className='individual'>
          <p className='in-label'>Nombre Paquete</p>
          <input type="text" value={paquete} onChange={InputPaqueteChange} className='in-txt'/>
        </section>
      </div>
    </div>
  )
}

export default App
