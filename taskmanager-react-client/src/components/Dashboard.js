import React, { Component } from "react";
import ProjectItem from "./Project/ProjectItem";
import CreateProjectButton from "./Project/CreateProjectButton";
import { connect } from "react-redux";
import { getProjects } from "../actions/projectActions";
import PropTypes from "prop-types";

class Dashboard extends Component {
  componentDidMount() {
    this.props.getProjects();
  }

  render() {
    //this.props.project refers to the state project and we are pulling off the array of projects
    const { projects } = this.props.project;
    return (
      <div className="projects">
        <div className="container">
          <div className="row">
            <div className="col-md-12">
              <h1 className="display-4 text-center">Projects</h1>
              <br />
              <CreateProjectButton />

              <br />
              <hr />
              {projects.map((project) => (
                <ProjectItem key={project.id} project={project} />
              ))}
            </div>
          </div>
        </div>
      </div>
    );
  }
}

Dashboard.propTypes = {
  getProjects: PropTypes.func.isRequired,
  project: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  //this refers to the state project which contains the array of projects and the one project when selected
  project: state.project,
});

export default connect(mapStateToProps, { getProjects })(Dashboard);
