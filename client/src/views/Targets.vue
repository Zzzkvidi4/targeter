<template>
    <div class="container">
        <div class="row">
            <div v-for="target in targets" class="col-md-4" v-bind:key="target.id">
                <div class="card mb-4">
                    <div class="card-body">
                        <p>{{target.text}}</p>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card mb-4">
                    <div class="card-body">
                        <router-link to="/target" class="nav-link">Добавить цель</router-link>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import TargetService from '../services/target.service';

    export default {
        name : 'TargetsView',
        data() {
            return {
                targets : []
            }
        },
        created() {
            const me = this;
            TargetService.getTargets().then(targets => me.targets = targets);
        }
    }
</script>