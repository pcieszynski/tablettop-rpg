import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './character.reducer';
import { ICharacter } from 'app/shared/model/character.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICharacterProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Character extends React.Component<ICharacterProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { characterList, match } = this.props;
    return (
      <div>
        <h2 id="character-heading">
          Characters
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp; Create new Character
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Level</th>
                <th>Experience</th>
                <th>Max Hitpoints</th>
                <th>Current Hitpoints</th>
                <th>Gold</th>
                <th>Strength</th>
                <th>Agility</th>
                <th>Constituition</th>
                <th>Intelligence</th>
                <th>Willpower</th>
                <th>Charisma</th>
                <th>Skill</th>
                <th>Game</th>
                <th>Status</th>
                <th>Item</th>
                <th>Profession</th>
                <th>Helmet</th>
                <th>Armour</th>
                <th>Boots</th>
                <th>Gloves</th>
                <th>Legs</th>
                <th>Right Hand</th>
                <th>Left Hand</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {characterList.map((character, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${character.id}`} color="link" size="sm">
                      {character.id}
                    </Button>
                  </td>
                  <td>{character.name}</td>
                  <td>{character.level}</td>
                  <td>{character.experience}</td>
                  <td>{character.maxHitpoints}</td>
                  <td>{character.currentHitpoints}</td>
                  <td>{character.gold}</td>
                  <td>{character.strength}</td>
                  <td>{character.agility}</td>
                  <td>{character.constituition}</td>
                  <td>{character.intelligence}</td>
                  <td>{character.willpower}</td>
                  <td>{character.charisma}</td>
                  <td>
                    {character.skills
                      ? character.skills.map((val, j) => (
                          <span key={j}>
                            <Link to={`skill/${val.id}`}>{val.id}</Link>
                            {j === character.skills.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {character.games
                      ? character.games.map((val, j) => (
                          <span key={j}>
                            <Link to={`game/${val.id}`}>{val.id}</Link>
                            {j === character.games.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {character.statuses
                      ? character.statuses.map((val, j) => (
                          <span key={j}>
                            <Link to={`status/${val.id}`}>{val.id}</Link>
                            {j === character.statuses.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {character.items
                      ? character.items.map((val, j) => (
                          <span key={j}>
                            <Link to={`item/${val.id}`}>{val.id}</Link>
                            {j === character.items.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>{character.professionId ? <Link to={`profession/${character.professionId}`}>{character.professionId}</Link> : ''}</td>
                  <td>{character.helmetId ? <Link to={`helmet/${character.helmetId}`}>{character.helmetId}</Link> : ''}</td>
                  <td>{character.armourId ? <Link to={`armour/${character.armourId}`}>{character.armourId}</Link> : ''}</td>
                  <td>{character.bootsId ? <Link to={`boots/${character.bootsId}`}>{character.bootsId}</Link> : ''}</td>
                  <td>{character.glovesId ? <Link to={`gloves/${character.glovesId}`}>{character.glovesId}</Link> : ''}</td>
                  <td>{character.legsId ? <Link to={`legs/${character.legsId}`}>{character.legsId}</Link> : ''}</td>
                  <td>{character.rightHandId ? <Link to={`right-hand/${character.rightHandId}`}>{character.rightHandId}</Link> : ''}</td>
                  <td>{character.leftHandId ? <Link to={`left-hand/${character.leftHandId}`}>{character.leftHandId}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${character.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${character.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${character.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ character }: IRootState) => ({
  characterList: character.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Character);
