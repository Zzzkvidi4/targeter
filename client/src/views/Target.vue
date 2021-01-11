<template>
    <div class="col-md-12">
        <category-modal/>
        <div class="card card-container">
            <form name="form" @submit.prevent="handleSave">
                <div class="form-group">
                    <label for="target">Цель</label>
                    <textarea v-model="text" class="form-control" name="target" v-validate="'required'"></textarea>
                    <div
                            v-if="errors.has('target')"
                            class="alert alert-danger"
                            role="alert"
                    >Цель обязательна</div>
                </div>
                <div class="form-group">
                    <label for="category">Категория</label>
                    <div class="row justify-content-start card-container">
                        <select v-model="selectedCategory" v-validate="'required'" name="category" class="form-control  col-sm-11">
                            <option v-for="category in categories" v-bind:value="category.id" v-bind:key="category.id">
                                {{ category.name }}
                            </option>
                        </select>
                        <button v-on:click="$modal.show('category-modal')" class="col-sm-1 form-control"><font-awesome-icon icon="plus" /></button>
                    </div>
                    <div
                            v-if="errors.has('category')"
                            class="alert alert-danger"
                            role="alert"
                    >Категория обязательна</div>
                </div>
                <div class="form-group">
                    <label for="status">Статус</label>
                    <select v-model="selectedStatus" v-validate="'required'" name="status" class="form-control">
                        <option v-for="status in statuses" v-bind:value="status.value" v-bind:key="status.value">
                            {{ status.displayName }}
                        </option>
                    </select>
                    <div
                            v-if="errors.has('status')"
                            class="alert alert-danger"
                            role="alert"
                    >Статус обязателен</div>
                </div>
                <div class="form-group">
                  <label for="cron">Расписание (cron)</label>
                  <input v-model="cron" class="form-control" name="cron">
                </div>
                <div class="form-group">
                    <button class="btn btn-primary btn-block" :disabled="loading">
                        <span v-show="loading" class="spinner-border spinner-border-sm"></span>
                        <span>Сохранить</span>
                    </button>
                </div>
                <div class="form-group">
                    <div v-if="message" class="alert alert-danger" role="alert">{{message}}</div>
                </div>
                <div>
                    <p>Для получения мотивационных сообщений через ВК необходимо разрешить уведомления</p>
                    <div id="vk_allow_messages_from_community"></div>
                    <VueScriptComponent script='<script type="text/javascript">VK.Widgets.AllowMessagesFromCommunity("vk_allow_messages_from_community", {}, 201671833);</script>'/>
                </div>
            </form>
        </div>
    </div>
</template>



<script>
    import StatusService from '../services/status.service';
    import CategoryService from '../services/category.service';
    import TargetService from '../services/target.service';
    import CategoryModal from "./CategoryModal.vue";
    import VueScriptComponent from 'vue-script-component'


    export default {
        name: 'Target',
        components: {
            CategoryModal,
            VueScriptComponent
        },
        data() {
            return {
                message: '',
                text: '',
                loading: false,
                statuses: [],
                selectedStatus: null,
                selectedCategory : null,
                cron : '',
                categories : []
            };
        },
        created() {
            StatusService.getStatuses().then(statuses => this.statuses = statuses);
            CategoryService.getCategories().then(categories => this.categories = categories);
        },
        methods: {
            handleSave() {
                const me = this;
                this.loading = true;
                this.$validator.validateAll().then(isValid => {
                    if (!isValid) {
                        this.loading = false;
                        return;
                    }
                    TargetService.createTarget({
                        text : me.text,
                        categoryId : me.selectedCategory,
                        status : me.selectedStatus,
                        cron : me.cron
                    }).then(() => {
                        this.loading = false;
                        this.$router.push('/targets');
                    });
                });
            }
        }
    };
</script>

<style scoped>
    label {
        display: block;
        margin-top: 10px;
    }

    .card-container.card {
        max-width: 600px !important;
        padding: 40px 40px;
    }

    .card {
        background-color: #f7f7f7;
        padding: 20px 25px 30px;
        margin: 0 auto 25px;
        margin-top: 50px;
        -moz-border-radius: 2px;
        -webkit-border-radius: 2px;
        border-radius: 2px;
        -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
        -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
        box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
    }

    .row {
        margin-right: 0;
        margin-left: 0;
    }
</style>