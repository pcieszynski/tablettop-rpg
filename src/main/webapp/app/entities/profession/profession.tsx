import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './profession.reducer';
import { IProfession } from 'app/shared/model/profession.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProfessionProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Profession extends React.Component<IProfessionProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { professionList, match } = this.props;
    return (
      <div>
        <h2 id="profession-heading">
          Professions
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp; Create new Profession
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Strength Modifier</th>
                <th>Agility Modifier</th>
                <th>Constituition Modifier</th>
                <th>Intelligence Modifier</th>
                <th>Willpower Modifier</th>
                <th>Charisma Modifier</th>
                <th>Skill</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {professionList.map((profession, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${profession.id}`} color="link" size="sm">
                      {profession.id}
                    </Button>
                  </td>
                  <td>{profession.name}</td>
                  <td>{profession.strengthModifier}</td>
                  <td>{profession.agilityModifier}</td>
                  <td>{profession.constituitionModifier}</td>
                  <td>{profession.intelligenceModifier}</td>
                  <td>{profession.willpowerModifier}</td>
                  <td>{profession.charismaModifier}</td>
                  <td>
                    {profession.skills
                      ? profession.skills.map((val, j) => (
                          <span key={j}>
                            <Link to={`skill/${val.id}`}>{val.id}</Link>
                            {j === profession.skills.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${profession.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${profession.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${profession.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ profession }: IRootState) => ({
  professionList: profession.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Profession);
