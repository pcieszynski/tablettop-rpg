import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name="Entities" id="entity-menu">
    <DropdownItem tag={Link} to="/entity/player">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Player
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/gamemaster">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Gamemaster
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/game">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Game
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/character">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Character
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/profession">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Profession
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/skill">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Skill
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/status">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Status
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/event">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Event
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/player-message">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Player Message
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/battle">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Battle
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/monster">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Monster
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/monster-type">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Monster Type
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/npc">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Npc
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/shop">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Shop
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/item">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Item
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/armour">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Armour
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/boots">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Boots
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/legs">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Legs
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/helmet">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Helmet
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/gloves">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Gloves
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/right-hand">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Right Hand
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/left-hand">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Left Hand
    </DropdownItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
